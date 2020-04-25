package com.example.demo.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("dev")
public class KafkaConfig {

    @Bean
    NewTopic libraryEvents(@Value("${spring.kafka.template.default-topic}") String topicName) {
        return TopicBuilder
                .name(topicName)
                .replicas(3)
                .partitions(3)
                .build();
    }
}
