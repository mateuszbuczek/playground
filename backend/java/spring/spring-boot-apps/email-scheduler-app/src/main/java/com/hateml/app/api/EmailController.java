package com.hateml.app.api;

import com.hateml.app.infrastructure.integration.quartz.EmailJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final Scheduler scheduler;

    @PostMapping("/scheduleEmail")
    public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@Valid @RequestBody ScheduleEmailRequest request) {
        try {

            ZonedDateTime dateTime = ZonedDateTime.of(request.getDateTime(), request.getTimeZone());

            if (dateTime.isBefore(ZonedDateTime.now())) {
                ScheduleEmailResponse response = new ScheduleEmailResponse(false, "dateTime must be after current time");
                return ResponseEntity.badRequest().body(response);
            }

            JobDetail jobDetail = buildJobDetail(request);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleEmailResponse response = new ScheduleEmailResponse(true, jobDetail.getKey().getName(),
                    jobDetail.getKey().getGroup(), "Email scheduler");
            return ResponseEntity.ok(response);
        } catch (SchedulerException ex) {
            log.error("Error scheduling email");

            ScheduleEmailResponse response = new ScheduleEmailResponse(false, "Error scheduling email");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    private JobDetail buildJobDetail(ScheduleEmailRequest request) {
        JobDataMap dataMap = new JobDataMap();

        dataMap.put("email", request.getEmail());
        dataMap.put("subject", request.getSubject());
        dataMap.put("body", request.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send email job")
                .usingJobData(dataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
