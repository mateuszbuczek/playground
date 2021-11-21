package com.example.multithreadedfileparser.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ProcessingThreadPoolConfig {

    @Bean
    Executor defaultExecutor() {
        return Executors.newCachedThreadPool();
    }
}
