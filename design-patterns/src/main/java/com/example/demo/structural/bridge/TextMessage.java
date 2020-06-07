package com.example.demo.structural.bridge;

public class TextMessage extends Message {
    public TextMessage(MessageSender messageSender) {
        super(messageSender);
    }

    @Override
    protected void send() {
        messageSender.send();
    }
}
