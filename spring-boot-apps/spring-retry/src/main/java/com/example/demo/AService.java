package com.example.demo;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AService {

    private final AtomicInteger counter = new AtomicInteger();

    @Retryable(value = RuntimeException.class)
    void defaultRetry() {
        int counter = this.counter.addAndGet(1);
        if (counter != 3 ) {
            throw new RuntimeException("exception");
        }
    }

    @Retryable(value = RuntimeException.class)
    public String defaultRetryWithRecover(String name) {
        throw new RuntimeException("exception");
    }

    @Recover
    public String recover(String name) {
        return "A" + name;
    }

    @Retryable(value = RuntimeException.class,
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000))
    public void customRetry() {
        int counter = this.counter.addAndGet(1);
        if (counter != 2 ) {
            throw new RuntimeException("exception");
        }
    }
}
