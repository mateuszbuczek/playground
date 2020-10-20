package com.example.demo;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Component
public class CustomJobListener implements JobListener {

    @Override
    public String getName() {
        return "some-name";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("to be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("vetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("executed");
    }
}
