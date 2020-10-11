package service;

import com.example.pb.CreateLaptopRequest;
import com.example.pb.CreateLaptopResponse;
import com.example.pb.Filter;
import com.example.pb.ImageInfo;
import com.example.pb.Laptop;
import com.example.pb.RateLaptopRequest;
import com.example.pb.RateLaptopResponse;
import com.example.pb.SearchLaptopRequest;
import com.example.pb.SearchLaptopResponse;
import com.example.pb.UploadImageRequest;
import com.example.pb.UploadImageResponse;
import com.google.protobuf.ByteString;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

public class LaptopService extends com.example.pb.LaptopServiceGrpc.LaptopServiceImplBase {
    private static final Logger logger = Logger.getLogger(LaptopService.class.getName());

    private final LaptopStore laptopStore;
    private ImageStore imageStore;
    private RatingStore ratingStore;

    public LaptopService(LaptopStore laptopStore, ImageStore imageStore, RatingStore ratingStore) {
        this.imageStore = imageStore;
        this.laptopStore = laptopStore;
        this.ratingStore = ratingStore;
    }

    @Override
    public void createLaptop(CreateLaptopRequest request, StreamObserver<CreateLaptopResponse> responseObserver) {
        com.example.pb.Laptop laptop = request.getLaptop();

        logger.info("got a create laptop request with id: " + request.getLaptop().getId());

        UUID uuid;

        if (request.getLaptop().getId().isEmpty()) {
            uuid = UUID.randomUUID();
        } else {
            try {
                uuid = UUID.fromString(request.getLaptop().getId());
            } catch (IllegalArgumentException e) {
                responseObserver.onError(
                        Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException()
                );
                return;
            }
        }

        Laptop other = laptop.toBuilder().setId(uuid.toString()).build();
        try {
            laptopStore.Save(other);
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException()
            );
            return;
        }

        CreateLaptopResponse response = CreateLaptopResponse.newBuilder().setId(other.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("saved laptop with ID: " + other.getId());
    }

    @Override
    // server streaming
    public void searchLaptop(SearchLaptopRequest request, StreamObserver<SearchLaptopResponse> responseObserver) {
        Filter filter = request.getFilter();

        try {
            laptopStore.Search(Context.current(), filter, (laptop) -> {
                logger.info("found laptop with ID: " + laptop.getId());
                SearchLaptopResponse response = SearchLaptopResponse.newBuilder().setLaptop(laptop).build();
                responseObserver.onNext(response);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responseObserver.onCompleted();
        logger.info("searching laptop completed");
    }

    @Override
    public StreamObserver<UploadImageRequest> uploadImage(StreamObserver<UploadImageResponse> responseObserver) {
        return new StreamObserver<UploadImageRequest>() {
            private String laptopId;
            private String imageType;
            private ByteArrayOutputStream imageData;

            @Override
            public void onNext(UploadImageRequest request) {
                if (request.getDataCase() == UploadImageRequest.DataCase.INFO) {
                    ImageInfo info = request.getInfo();
                    logger.info("receive image info: \n" + info);

                    laptopId = info.getLaptopId();
                    imageType = info.getImageType();
                    imageData = new ByteArrayOutputStream();

                    return;
                }

                ByteString chunkData = request.getChunkData();
                logger.info("receive image chunk with size: " + chunkData.size());

                if (imageData==null) {
                    logger.info("image info wasnt sent before");
                    responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("image info wasnt sent before").asRuntimeException());
                    return;
                }

                try {
                    chunkData.writeTo(imageData);
                } catch (IOException e) {
                    responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("error saving chunk data").asRuntimeException());
                    return;
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.warning(t.getMessage());
            }

            @Override
            public void onCompleted() {
                String imageId = "";
                int imageSize = imageData.size();
                try {
                    imageId = imageStore.save(laptopId, imageType, imageData);
                } catch (IOException e) {
                    responseObserver.onError(Status.INTERNAL.withDescription("error during saving image").asRuntimeException());
                }

                UploadImageResponse response = com.example.pb.UploadImageResponse.newBuilder()
                        .setId(imageId)
                        .setSize(imageSize)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<RateLaptopRequest> rateLaptop(StreamObserver<RateLaptopResponse> responseObserver) {
        return new StreamObserver<RateLaptopRequest>() {
            @Override
            public void onNext(RateLaptopRequest request) {
                String laptopId = request.getLaptopId();
                float score = request.getScore();
                logger.info("receivd rate laptop request: id =" + laptopId);

                Laptop found = laptopStore.Find(laptopId);
                if (found == null) {
                    responseObserver.onError(
                            Status.NOT_FOUND.withDescription("laptop Id does not exist").asRuntimeException()
                    );
                    return;
                }

                Rating rating = ratingStore.add(laptopId, score);
                RateLaptopResponse response = RateLaptopResponse.newBuilder()
                        .setLaptopId(laptopId)
                        .setRatedCount(rating.getCount())
                        .setAverageScore(rating.getSum() / rating.getCount())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                logger.warning(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
