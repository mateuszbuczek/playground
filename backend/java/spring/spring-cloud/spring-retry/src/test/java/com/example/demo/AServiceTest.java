package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AServiceTest {

    @Autowired
    AService service;

    @Autowired
    RetryTemplate retryTemplate;

    @Test
    void defaultRetry() {
        assertDoesNotThrow(() -> service.defaultRetry());
    }

    @Test
    void defaultRetryWithRecover() {
        assertEquals("ATom", service.defaultRetryWithRecover("Tom"));
    }

    @Test
    void customRetry() {
        assertDoesNotThrow(() -> service.customRetry());
    }

    @Test
    void retryTemplate() {
        assertThrows(RuntimeException.class, () -> retryTemplate.execute(context -> {
            service.defaultRetry();
            return null;
        }));
    }
}