package com.example.demo.structural.proxy;

public class Main {

    public static void main(String[] args) {
        new ServiceProxy(new ServiceImpl()).get();
    }
}
