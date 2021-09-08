import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    void firstMono() {
        Mono.just("A")
                .log()
                .doOnSubscribe(subscription -> System.out.println("Subscribed: " + subscription))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(success -> System.out.println("Success: " + success))
                .subscribe(System.out::println);
    }

    @Test
    void monoError() {
        Mono.error(new RuntimeException())
                .log()
                .doOnSubscribe(subscription -> System.out.println("Subscribed: " + subscription))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(success -> System.out.println("Success: " + success))
                .subscribe(System.out::println);
    }
}
