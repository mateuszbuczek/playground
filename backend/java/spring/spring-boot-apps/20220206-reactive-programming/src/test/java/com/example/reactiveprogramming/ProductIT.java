package com.example.reactiveprogramming;

import com.example.reactiveprogramming.product.Product;
import com.example.reactiveprogramming.product.ProductController;
import com.example.reactiveprogramming.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
public class ProductIT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService service;

    @Test
    public void addProductTest() {
        Mono<Product> product = Mono.just(new Product(1, "a", 2, 12.2));
        when(service.saveProduct(product))
                .thenReturn(product);

        webTestClient.post().uri("/products").body(product, Product.class).exchange().expectStatus().isOk();
    }


    @Test
    public void getProductsTest() {
        Flux<Product> products = Flux.just(
                new Product(1, "a", 2, 12.2),
                new Product(1, "a", 2, 12.2)
        );
        when(service.getProducts())
                .thenReturn(products);

        Flux<Product> responseBody = webTestClient.post().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext( new Product(1, "a", 2, 12.2))
                .expectNext( new Product(1, "a", 2, 12.2))
                .verifyComplete();
    }
}
