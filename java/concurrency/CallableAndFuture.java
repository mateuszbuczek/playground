import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CallableAndFuture {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Callable<String>> callables = IntStream.range(0, 100)
                .mapToObj(num -> getCallable(String.valueOf(num)))
                .collect(Collectors.toList());

        List<Future<String>> futures = executor.invokeAll(callables);

        System.out.println(futures.get(0).isDone());

        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        executor.shutdown();
    }

    private static Callable<String> getCallable(String id) {
        return () -> id + " :" + Thread.currentThread().getName();
    }
}
