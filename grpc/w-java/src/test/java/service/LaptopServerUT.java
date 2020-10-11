package service;

import com.example.pb.CreateLaptopRequest;
import com.example.pb.CreateLaptopResponse;
import com.example.pb.Laptop;
import com.example.pb.LaptopServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

public class LaptopServerUT extends TestCase {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    private LaptopStore store;
    private LaptopServer server;
    private ManagedChannel channel;

    @Override
    protected void setUp() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        InProcessServerBuilder serverBuilder = InProcessServerBuilder.forName(serverName).directExecutor();

        store = new InMemoryLaptopStore();
        server = new LaptopServer(serverBuilder, 0, store);
        server.start();

        channel = grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build());
    }

    @Override
    protected void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void createLaptopWithId() {
        Laptop laptop = Laptop.newBuilder().setId(UUID.randomUUID().toString()).build();

        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

        LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
        CreateLaptopResponse response = stub.createLaptop(request);

        assertNotNull(response);
        assertEquals(laptop.getId(), response.getId());

        Laptop found = store.Find(laptop.getId());
        assertNotNull(found);
    }
}
