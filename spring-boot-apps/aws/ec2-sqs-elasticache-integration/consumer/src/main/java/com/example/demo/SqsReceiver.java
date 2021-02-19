package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqsReceiver {

    @SqsListener("user-cache-details")
    public void userCacheListener(User user) {
      log.info("user to cache: {}", user);
    }
}
