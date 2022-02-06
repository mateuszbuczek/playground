package com.example.reactiveprogramming.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Flux<Product> getProducts() {
        return repository.findAll();
    }

    public Mono<Product> getProduct(Integer id) {
        return repository.findById(id);
    }

    public Flux<Product> getProductInRange(double min, double max) {
        return repository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<Product> saveProduct(Mono<Product> product) {
        return product.flatMap(repository::insert);
    }

    public Mono<Product> updateProduct(Mono<Product> product, Integer id) {
        return repository.findById(id)
                .map(found -> {
                    BeanUtils.copyProperties(product, found);
                    return found;
                })
                .flatMap(repository::save);
    }

    public Mono<Void> deleteProduct(Integer id) {
        return repository.deleteById(id);
    }
}
