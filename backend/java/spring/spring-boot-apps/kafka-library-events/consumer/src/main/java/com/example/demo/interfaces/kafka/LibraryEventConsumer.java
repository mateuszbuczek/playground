package com.example.demo.interfaces.kafka;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.infrastructure.JpaLibraryEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryEventConsumer {

    private final ObjectMapper objectMapper;
    private final JpaLibraryEventRepository repository;

    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer, String> record) throws JsonProcessingException {
        log.info("record: {}", record);

        LibraryEvent event = objectMapper.readValue(record.value(), LibraryEvent.class);

        repository.save(event);
    }
}
