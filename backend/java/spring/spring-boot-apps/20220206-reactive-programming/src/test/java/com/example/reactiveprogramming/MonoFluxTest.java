package com.example.reactiveprogramming;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<?> data = Mono.just("data")
                .then(Mono.error(new RuntimeException("exception occurred")))
                .log();
        data.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux() {
        Flux<String> data = Flux.just("a", "b", "c", "d")
                .concatWithValues("e")
                .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .concatWithValues("f")
                .log();
        data.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }
}
