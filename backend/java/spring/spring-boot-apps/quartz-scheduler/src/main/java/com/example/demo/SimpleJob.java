package com.example.demo;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SimpleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("SimpleJobExecuted " + context.getJobDetail().getJobDataMap().get("user") + " " + LocalDateTime.now());
    }
}
