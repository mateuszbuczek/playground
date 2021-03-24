package com.example.demo.structural.proxy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServiceProxy implements Service {

    private final ServiceImpl service;

    @Override
    public void get() {
        System.out.println("before service invocation");
        service.get();
        System.out.println("after service invocation");
    }
}
