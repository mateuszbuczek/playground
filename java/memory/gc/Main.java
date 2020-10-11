import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        long availableBytes = runtime.freeMemory();
        System.out.println("Available memory at start: " + availableBytes / (1024 * 1024) + "MB");

        ArrayList<Customer> customers = new ArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            customers.add(new Customer(Integer.toString(i)));
        }

        availableBytes = runtime.freeMemory();
        System.out.println("Available memory when customers created: " + availableBytes / (1024 * 1024) + "MB");

        customers = new ArrayList<>();

        availableBytes = runtime.freeMemory();
        System.out.println("Available memory when customer no longer referenced: " + availableBytes / (1024 * 1024) + "MB");

        Thread.sleep(1000);

        availableBytes = runtime.freeMemory();
        System.out.println("Available memory 1 sec later: " + availableBytes / (1024 * 1024) + "MB");

        System.gc();

        availableBytes = runtime.freeMemory();
        System.out.println("Available memory after GC command: " + availableBytes / (1024 * 1024) + "MB");
    }

    private static class Customer {
        private String name;

        public Customer(String name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("this object is being gc'd: Customer: " + this.name);
        }
    }
}
