package com.example.demo;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.example.demo.Application.class, args);
    }

    @EventListener
    public void handle(ApplicationStartedEvent event) throws IOException, InterruptedException {
        Connection connect = Nats.connect();

        Dispatcher dispatcher = connect.createDispatcher(message ->
                System.out.println("Message: "
                        + new String(message.getData(), StandardCharsets.UTF_8)
                        + " " + message.getSID()
                        + " " + message.getSubject()));

        dispatcher.subscribe("com.asd");
    }
}
