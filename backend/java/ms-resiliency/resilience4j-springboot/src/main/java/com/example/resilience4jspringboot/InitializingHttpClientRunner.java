package com.example.resilience4jspringboot;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class InitializingHttpClientRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;

    @Override
    @Retry(name = "testRetryer")
    public void run(String... args) throws Exception {
        restTemplate.getForObject("http://hateml.com", Void.class);
    }
}
