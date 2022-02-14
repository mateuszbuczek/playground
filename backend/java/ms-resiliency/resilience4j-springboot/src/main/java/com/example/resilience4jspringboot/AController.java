package com.example.resilience4jspringboot;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AController {

    private final RestTemplate restTemplate;

    @GetMapping
    @Retry(name = "testretryer")
    public String get() {
        log.info("invoked()");
        return restTemplate.getForObject("http://hateml.com", String.class);
    }

    @GetMapping("/cb")
    @CircuitBreaker(name = "testcb", fallbackMethod = "getDefault")
    public String getCB() {
        log.info("invoked()");
        if (Math.random() > .5) {
            return "string";
        } else {
            throw new RuntimeException();
        }
    }

    public String getDefault(Exception e) {
        return "default string";
    }
}
