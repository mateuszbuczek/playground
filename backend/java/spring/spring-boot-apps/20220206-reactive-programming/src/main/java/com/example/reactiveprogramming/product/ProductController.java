package com.example.reactiveprogramming.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public Flux<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable Integer id) {
        return service.getProduct(id);
    }

    @GetMapping("/range")
    public Flux<Product> getInRange(@RequestParam double min, @RequestParam double max) {
        return service.getProductInRange(min, max);
    }

    @PostMapping
    public Mono<Product> saveProduct(@RequestBody Mono<Product> product) {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@RequestBody Mono<Product> productMono, Integer id) {
        return service.updateProduct(productMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Integer id) {
        return service.deleteProduct(id);
    }
}
