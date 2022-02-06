package com.example.reactiveprogramming.customer;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CustomerRepository {

    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1, 10)
                .peek(num -> System.out.println("Fetching: " + num))
                .peek(num -> sleepExec())
                .mapToObj(num -> new Customer(num, "customer" + num))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersStream() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(num -> System.out.println("Fetching in stream flow: " + num))
                .map(num -> new Customer(num, "customer" + num));
    }

    @SneakyThrows
    private static void sleepExec() {
        Thread.sleep(1000);
    }
}
