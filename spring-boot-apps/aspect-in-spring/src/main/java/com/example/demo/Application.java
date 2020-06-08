package com.example.demo;

import com.example.demo.executiontime.Service;
import com.example.demo.translator.BlogText;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final Service service;
    private final BlogText blogText;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    public void onEvent(ApplicationReadyEvent event) {
        service.randomString();
//        service.throwException();

        blogText.create("asd").read();
    }
}
