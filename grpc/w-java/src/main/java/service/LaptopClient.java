package service;

import com.example.pb.CreateLaptopRequest;
import com.example.pb.Filter;
import com.example.pb.Laptop;
import com.example.pb.LaptopServiceGrpc;
import com.example.pb.SearchLaptopRequest;
import com.example.pb.SearchLaptopResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LaptopClient {
    private static final Logger logger = Logger.getLogger(LaptopClient.class.getName());

    private final ManagedChannel channel;
    private final LaptopServiceGrpc.LaptopServiceBlockingStub blockingStub;

    public LaptopClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        LaptopClient laptopClient = new LaptopClient("0.0.0.0", 8090);

        Laptop laptop = Laptop.newBuilder().setId(UUID.randomUUID().toString()).build();

        try {
            for (int i = 0; i < 10; i++) {
                laptopClient.createLaptop(laptop);
            }

            Filter filter = Filter.newBuilder().setMaxPriceUsd(20).build();
            laptopClient.searchLaptop(filter);
        } finally {
            laptopClient.shutdown();
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
