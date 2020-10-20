package com.example.demo;

import com.example.demo.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AController {

    private final ProductRepository repository;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping
    public List<Object> findAll() {
        return repository.findAll();
    }
}
