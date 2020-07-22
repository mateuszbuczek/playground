package com.example.demo;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationEventsLogger implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(">>>>>>>>>>>>> EVENT WAS PUBLISHED -> " + event.getClass().getName());
    }
}
