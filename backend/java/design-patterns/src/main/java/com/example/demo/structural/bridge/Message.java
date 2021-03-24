package com.example.demo.structural.bridge;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Message {
    protected MessageSender messageSender;

    protected abstract void send();
}
