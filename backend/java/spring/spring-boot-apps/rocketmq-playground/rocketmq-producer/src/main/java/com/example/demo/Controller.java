package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final RocketMQTemplate template;
    private final String topicT1 = "topict1";

    @GetMapping("/t1")
    public void t1() {
        template.send(topicT1, MessageBuilder
                .withPayload(User.createRandom())
                .setHeader("uuid", UUID.randomUUID().toString())
                .build()
        );
    }
}
