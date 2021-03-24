package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final AService aService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    public void handle(ApplicationReadyEvent event) {
        aService.handle("a");
    }
}
