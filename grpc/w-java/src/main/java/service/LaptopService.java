package service;

import com.example.pb.CreateLaptopRequest;
import com.example.pb.CreateLaptopResponse;
import com.example.pb.Filter;
import com.example.pb.Laptop;
import com.example.pb.SearchLaptopRequest;
import com.example.pb.SearchLaptopResponse;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.UUID;
import java.util.logging.Logger;

public class LaptopService extends com.example.pb.LaptopServiceGrpc.LaptopServiceImplBase {
    private static final Logger logger = Logger.getLogger(LaptopService.class.getName());

    private final LaptopStore laptopStore;

    public LaptopService(LaptopStore laptopStore) {
        this.laptopStore = laptopStore;
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
    public void searchLaptop(SearchLaptopRequest request, StreamObserver<SearchLaptopResponse> responseObserver) throws InterruptedException {
        Filter filter = request.getFilter();

        laptopStore.Search(Context.current(), filter, (laptop) -> {
            logger.info("found laptop with ID: " + laptop.getId());
            SearchLaptopResponse response = SearchLaptopResponse.newBuilder().setLaptop(laptop).build();
            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
        logger.info("searching laptop completed");
    }
}
