package com.example.demo.infrastructure;

import com.example.demo.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveTransactionRepository extends ReactiveCrudRepository<Order, String> {
}
