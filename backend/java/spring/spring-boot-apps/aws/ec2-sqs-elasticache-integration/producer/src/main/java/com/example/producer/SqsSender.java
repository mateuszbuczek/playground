package com.example.producer;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsSender {

    private final QueueMessagingTemplate template;

    public SqsSender(AmazonSQSAsync sqsAsync) {
        this.template = new QueueMessagingTemplate(sqsAsync);
    }

    public void send(User user) {
        template.convertAndSend("user-cache-details", user);
    }
}
