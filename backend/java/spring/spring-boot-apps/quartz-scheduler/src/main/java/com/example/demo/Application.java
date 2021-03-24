package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final Scheduler scheduler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    public void handle(ApplicationStartedEvent event) throws SchedulerException {
        JobDetail detail = JobBuilder.newJob()
                .withIdentity("some-name")
                .ofType(SimpleJob.class)
                .usingJobData("user", "tom")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger().startAt(new Date()).build();

        scheduler.scheduleJob(detail, trigger);
    }
}
