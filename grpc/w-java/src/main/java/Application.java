import service.DiskImageStore;
import service.InMemoryLaptopStore;
import service.InMemoryRatingStore;
import service.LaptopServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        InMemoryLaptopStore inMemoryLaptopStore = new InMemoryLaptopStore();
        DiskImageStore diskImageStore = new DiskImageStore("img");
        InMemoryRatingStore inMemoryRatingStore = new InMemoryRatingStore();
        LaptopServer laptopServer = new LaptopServer(8090, inMemoryLaptopStore, diskImageStore, inMemoryRatingStore);
        laptopServer.start();
        laptopServer.blockUntilShutdown();
    }
}
