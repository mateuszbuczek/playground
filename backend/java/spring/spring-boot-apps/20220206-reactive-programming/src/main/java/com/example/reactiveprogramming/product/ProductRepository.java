package com.example.reactiveprogramming.product;

import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, Integer> {

    <T extends Comparable<T>> Flux<Product> findByPriceBetween(Range<Double> closed);
}
