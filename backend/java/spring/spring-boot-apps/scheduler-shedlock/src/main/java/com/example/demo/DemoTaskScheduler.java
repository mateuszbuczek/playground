package com.example.demo;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DemoTaskScheduler {

    @Scheduled(fixedRate = 1000)
    @SchedulerLock(name = "some-custom-task", lockAtLeastForString = "PT10S", lockAtMostForString = "PT20S")
    public void task() {
        System.out.println(LocalDateTime.now().toString() + " executed");
    }
}
