package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class AService {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public Integer lengthOfName(String name) {
        return name.length();
    }
}
