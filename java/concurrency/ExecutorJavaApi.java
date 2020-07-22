import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorJavaApi {

    public static void main(String[] args) {
        singleThreaded();
        multiThreaded();
    }

    private static void multiThreaded() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(getRunnableA());
        executorService.submit(getRunnableB());
        executorService.submit(getRunnableA());

        /* application wont stop by its own as its waiting for new runnables*/
        executorService.shutdown();
    }

    private static void singleThreaded() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(getRunnableA());

        /* application wont stop by its own as its waiting for new runnables*/
        executorService.shutdown();
    }

    private static Runnable getRunnableA() {
        return () -> System.out.println("A Inside: " + Thread.currentThread().getName());
    }

    private static Runnable getRunnableB() {
        return () -> System.out.println("B Inside: " + Thread.currentThread().getName());
    }
}
