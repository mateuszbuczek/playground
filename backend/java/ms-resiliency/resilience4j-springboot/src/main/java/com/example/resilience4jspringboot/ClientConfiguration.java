package com.example.resilience4jspringboot;

import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ClientConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(1)).build();
    }

    @Bean
    RetryConfigCustomizer retryConfigCustomizer() {
        return RetryConfigCustomizer.of("testretryer", configbuilder -> {
            configbuilder.maxAttempts(4).waitDuration(Duration.ofSeconds(1)).build();
        });
    }
}
