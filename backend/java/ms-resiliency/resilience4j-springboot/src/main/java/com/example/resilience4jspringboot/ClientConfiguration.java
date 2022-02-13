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
        return new RestTemplateBuilder().build();
    }

    @Component
    static class RetryConfig implements RetryConfigCustomizer {

        @Override
        public void customize(io.github.resilience4j.retry.RetryConfig.Builder configBuilder) {
            configBuilder.maxAttempts(3);
            configBuilder.waitDuration(Duration.ofSeconds(1));
        }

        @Override
        public String name() {
            return "testRetryer";
        }
    }
}
