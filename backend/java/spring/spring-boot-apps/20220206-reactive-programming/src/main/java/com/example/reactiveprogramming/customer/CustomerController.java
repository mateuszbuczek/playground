package com.example.reactiveprogramming.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return service.loadAllCustomers();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getAllCustomersStream() {
        return service.loadAllCustomersStream();
    }

    @Configuration
    public static class RouterConfig {

        @Bean
        public RouterFunction<ServerResponse> routerFunction(CustomerHandler handler) {
            return RouterFunctions.route()
                    .GET("/router/customers", handler::loadCustomers)
                    .build();
        }
    }

    @Component
    @RequiredArgsConstructor
    static class CustomerHandler {
        private final CustomerRepository repository;

        public Mono<ServerResponse> loadCustomers(ServerRequest request) {
            Flux<Customer> customersStream = repository.getCustomersStream();
            return ServerResponse.ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(customersStream, Customer.class);
        }
    }
}
