import service.DiskImageStore;
import service.InMemoryLaptopStore;
import service.LaptopServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        InMemoryLaptopStore inMemoryLaptopStore = new InMemoryLaptopStore();
        DiskImageStore diskImageStore = new DiskImageStore("img");
        LaptopServer laptopServer = new LaptopServer(8090, inMemoryLaptopStore, diskImageStore);
        laptopServer.start();
        laptopServer.blockUntilShutdown();
    }
}
