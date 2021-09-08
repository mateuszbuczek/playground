import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxTest {

    @Test
    void test() {
        Flux.just("A","B","C")
                .log()
                .subscribe();
    }

    @Test
    void interval() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(3)
                .subscribe();
        Thread.sleep(5000);
    }

    @Test
    void range() {
        Flux.range(10, 5)
                .log()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        subscription.request(3);
                    }
                });
    }
}
