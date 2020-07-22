package com.example.demo.structural.bridge;

public class TextMessageSender implements MessageSender {

    @Override
    public void send() {
        System.out.println("Send text message");
    }
}
