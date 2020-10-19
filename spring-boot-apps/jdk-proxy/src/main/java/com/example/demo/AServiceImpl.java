package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class AServiceImpl implements AService {

    @Override
    public boolean handle(String val) {
        System.out.println("service arg: " + val);
        return false;
    }
}
