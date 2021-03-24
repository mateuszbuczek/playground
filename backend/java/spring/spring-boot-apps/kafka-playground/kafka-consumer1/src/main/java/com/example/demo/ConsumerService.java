package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerService {

    private final KafkaConsumer<String, String> consumer;

    public void consume() {
        consumer.subscribe(Collections.singletonList("my-custom-topic-name"));

        try {
            while (true) {
                ConsumerRecords<String, String> poll = consumer.poll(100);
                for (ConsumerRecord<String, String> p : poll) {
                    log.info(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
                            p.topic(), p.partition(), p.offset(), p.key(), p.value()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
