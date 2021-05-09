package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AService {

    private final FruitRepository repository;
    private final Scheduler scheduler;

    @SneakyThrows
    @PostConstruct
    void init() {
        scheduler.start();
    }

    public Fruit save(Fruit fruit) {
        return repository.save(fruit);
    }

    public List<Fruit> findFruits() {
        return (List<Fruit>) repository.findAll();
    }

    public void deleteFruit(Long fruitId) {
        repository.findById(fruitId).ifPresent(repository::delete);
    }

    public long countByFruitName(String fruitName) {
        repository.findUniqueFruitNames().forEach(System.out::println);
        return repository.countByName(fruitName);
    }

    @SneakyThrows
    public void schedule(JobData data) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("some-key", "some-val");

        JobDetail job = JobBuilder.newJob(ScheduledJob.class)
                .withIdentity(data.getJobName(), data.getJobGroup())
                .usingJobData(jobDataMap)
                .storeDurably(false)
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(data.getJobName(), data.getJobGroup())
                .forJob(job)
                .startNow()
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(data.getGapDuration())
                        .withRepeatCount(data.getCounter())
                )
                .build();

        scheduler.scheduleJob(trigger);
    }
}
