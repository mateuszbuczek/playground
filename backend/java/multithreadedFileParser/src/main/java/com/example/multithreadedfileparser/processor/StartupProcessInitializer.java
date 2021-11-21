package com.example.multithreadedfileparser.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StartupProcessInitializer {

    private final Processor processor;

    @Bean
    CommandLineRunner startupProcessRunner() {
        return (any) -> processor.startProcessing("C:\\workspace\\multithreadedFileParser");
    }
}
