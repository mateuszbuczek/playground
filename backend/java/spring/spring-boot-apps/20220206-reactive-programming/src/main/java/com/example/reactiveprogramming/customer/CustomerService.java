package com.example.reactiveprogramming.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    List<Customer> loadAllCustomers() {
        long start = System.currentTimeMillis();
        List<Customer> customers = repository.getCustomers();
        long end = System.currentTimeMillis();
        System.out.println("loadAllCustomers execution time: " + (end -start));
        return customers;
    }

    Flux<Customer> loadAllCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers = repository.getCustomersStream();
        long end = System.currentTimeMillis();
        System.out.println("loadAllCustomers execution time: " + (end -start));
        return customers;
    }
}
