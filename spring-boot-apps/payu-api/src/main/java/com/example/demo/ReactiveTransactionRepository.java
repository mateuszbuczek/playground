package com.example.demo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveTransactionRepository extends ReactiveCrudRepository<Order, String> {
}
