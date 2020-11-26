package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ReactiveTransactionRepository repository;

    @GetMapping
    public Flux<Order> getOrders() {
        return repository.findAll();
    }

    @PostMapping
    public Mono<Order> makeOrder() {
        Order sampleOrder = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderId(UUID.randomUUID().toString())
                .build();

        return repository.save(sampleOrder);
    }
}
