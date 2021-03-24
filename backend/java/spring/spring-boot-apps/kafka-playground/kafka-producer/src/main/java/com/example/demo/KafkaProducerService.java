package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaProducer<String, String> producer;

    public void produce() {
        try {
            while (true) {
                IntStream.range(0, new Random().nextInt(1000))
                        .mapToObj(this::createProducerRecord)
                        .forEach(producer::send);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ProducerRecord<String, String> createProducerRecord(Integer i) {
        return new ProducerRecord<>(
                "my-custom-topic-name",
                Integer.toString(i),
                "CUSTOM PAYLOAD: " + i
        );
    }
}
