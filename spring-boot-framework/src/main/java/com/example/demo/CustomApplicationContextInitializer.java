package com.example.demo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class CustomApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println(">>>>>>>>>>>>> Application context initializer, available ConfigurableApplicationContext");
//        System.out.println((GenericApplicationContext) applicationContext);
    }
}
