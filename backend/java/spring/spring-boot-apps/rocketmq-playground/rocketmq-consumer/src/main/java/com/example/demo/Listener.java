package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "topict1",
        consumerGroup = "t1-consumer-group"
)
public class Listener implements RocketMQListener<User> {

    @Override
    public void onMessage(User message) {
      log.info("MESSAGE: " + message.toString());
    }
}
