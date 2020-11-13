package com.example.demo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class CustomApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println(">>>>>>>>>>>>>>>  ApplicationContextInitializer, available ConfigurableApplicationContext");
//        System.out.println((GenericApplicationContext) applicationContext);
    }
}
