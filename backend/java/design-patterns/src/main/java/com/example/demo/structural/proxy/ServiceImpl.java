package com.example.demo.structural.proxy;

public class ServiceImpl implements Service {
    @Override
    public void get() {
        System.out.println("service get() method");
    }
}
