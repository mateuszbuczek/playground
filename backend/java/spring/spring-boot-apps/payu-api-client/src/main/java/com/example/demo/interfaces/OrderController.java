package com.example.demo.interfaces;

import com.example.demo.domain.Order;
import com.example.demo.infrastructure.PaymentGatewayClient;
import com.example.demo.infrastructure.ReactiveTransactionRepository;
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
  private final PaymentGatewayClient client;

  @GetMapping
  public Flux<Order> getOrders() {
    return repository.findAll();
  }

  @PostMapping
  public Mono<Order> makeOrder() {
    Order sampleOrder =
        Order.builder()
            .id(UUID.randomUUID().toString())
            .orderId(UUID.randomUUID().toString())
            .build();

    return repository.save(sampleOrder);
  }

  //TODO remove from here
  @GetMapping("/shops")
  public Mono<Object> getShopDetails() {
    return client.getShopDetails();
  }
}
