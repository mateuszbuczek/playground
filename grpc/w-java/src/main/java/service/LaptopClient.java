package service;

import com.example.pb.CreateLaptopRequest;
import com.example.pb.Filter;
import com.example.pb.ImageInfo;
import com.example.pb.Laptop;
import com.example.pb.LaptopServiceGrpc;
import com.example.pb.SearchLaptopRequest;
import com.example.pb.SearchLaptopResponse;
import com.example.pb.UploadImageRequest;
import com.example.pb.UploadImageResponse;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LaptopClient {
    private static final Logger logger = Logger.getLogger(LaptopClient.class.getName());

    private final ManagedChannel channel;
    private static LaptopServiceGrpc.LaptopServiceBlockingStub blockingStub;
    private static LaptopServiceGrpc.LaptopServiceStub asyncStub;

    public LaptopClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
        asyncStub = LaptopServiceGrpc.newStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        LaptopClient laptopClient = new LaptopClient("0.0.0.0", 8090);

        Laptop laptop = Laptop.newBuilder().setId(UUID.randomUUID().toString()).build();

        try {
            // tes tcreate and serach laptop
//            for (int i = 0; i < 10; i++) {
//                laptopClient.createLaptop(laptop);
//            }
//
//            Filter filter = Filter.newBuilder().setMaxPriceUsd(20).build();
//            laptopClient.searchLaptop(filter);

            // test upload image
            laptopClient.createLaptop(laptop);
            uploadImage(laptop.getId(), "tmp/test_image.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            laptopClient.shutdown();
        }
    }

    private static void uploadImage(String id, String imagePath) throws InterruptedException, FileNotFoundException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<UploadImageRequest> requestObserver = asyncStub.withDeadlineAfter(5, TimeUnit.SECONDS)
                .uploadImage(new StreamObserver<UploadImageResponse>() {
                    @Override
                    public void onNext(UploadImageResponse value) {
                        logger.info("receive response: \n" + value);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logger.log(Level.SEVERE, "upload failed: " + t);
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        logger.info("uploaded");
                        finishLatch.countDown();
                    }
                });

        FileInputStream fileInputStream = new FileInputStream(imagePath);
        String fileExtension = imagePath.substring(imagePath.lastIndexOf("."));

        ImageInfo info = ImageInfo.newBuilder()
                .setLaptopId(id)
                .setImageType(fileExtension)
                .build();

        UploadImageRequest request = UploadImageRequest.newBuilder().setInfo(info).build();

        try {
            requestObserver.onNext(request);
            logger.info("sent image info: \n" + info);

            byte[] buffer = new byte[1024];
            while (true) {
                int read = fileInputStream.read(buffer);
                if (read <= 0) {
                    break;
                }

                if (finishLatch.getCount() ==0) {
                    return;
                }

                request = UploadImageRequest.newBuilder()
                        .setChunkData(ByteString.copyFrom(buffer, 0, read))
                        .build();

                requestObserver.onNext(request);
                logger.info("sent image chunk with size: " + read);
            }
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "unexptected error: " + exception.getMessage());
            requestObserver.onError(exception);
            return;
        }

        requestObserver.onCompleted();

        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            logger.warning("request cannot finish in 1 minute");
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void createLaptop(com.example.pb.Laptop laptop) {
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();
        com.example.pb.CreateLaptopResponse response = com.example.pb.CreateLaptopResponse.getDefaultInstance();

        try {
            response = blockingStub.createLaptop(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "request failed: "+ e.getMessage());
        }

        logger.info("laptop created with ID: " + response.getId());
    }

    private void searchLaptop(Filter filter) {
        logger.info("search started");
        SearchLaptopRequest request = SearchLaptopRequest.newBuilder().setFilter(filter).build();
        Iterator<SearchLaptopResponse> responseIterator = blockingStub
                .withDeadlineAfter(5, TimeUnit.SECONDS)
                .searchLaptop(request);

        while (responseIterator.hasNext()) {
            SearchLaptopResponse next = responseIterator.next();
            logger.info("Found: " + next.getLaptop().getId());
        }

        logger.info(" search completed");
    }


}
