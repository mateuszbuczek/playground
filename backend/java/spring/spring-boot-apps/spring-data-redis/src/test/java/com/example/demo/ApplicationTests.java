package com.example.demo;

import com.example.demo.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void contextLoads() {
        Product product = Product.builder().name("some-name").build();
        Product savedProduct = productRepository.save(product);
        assert savedProduct.getId() != 0;
    }

}
