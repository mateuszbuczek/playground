package com.example.demo.infrastructure.kafka;

import com.example.demo.domain.Book;
import com.example.demo.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryEventPublisherUT {

    @Mock
    KafkaTemplate<Integer, String> template;

    @Spy
    ObjectMapper objectMapper;

    @InjectMocks
    LibraryEventPublisher publisher;

    @Test
    void publish2_failure() throws JsonProcessingException, ExecutionException, InterruptedException {
        // given
        Book book = Book.builder()
                .id(123)
                .author("tom")
                .name("asd")
                .build();

        LibraryEvent event = LibraryEvent.builder()
                .book(book)
                .build();

        //and
        SettableListenableFuture future = new SettableListenableFuture();

        future.setException(new RuntimeException("Exception calling kafka"));

        when(template.send(isA(ProducerRecord.class))).thenReturn(future);
        // expect
        assertThrows(Exception.class, () -> publisher.publishCreate(event).get());
    }

    @Test
    void publish2_succes() throws JsonProcessingException, ExecutionException, InterruptedException {
        // given
        Book book = Book.builder()
                .id(123)
                .author("tom")
                .name("asd")
                .build();

        LibraryEvent event = LibraryEvent.builder()
                .book(book)
                .build();

        //and
        SettableListenableFuture future = new SettableListenableFuture();

        String str = objectMapper.writeValueAsString(event);
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>("library-events", event.getId(), str);
        RecordMetadata metadata = new RecordMetadata(new TopicPartition("any", 1), 1L, 1L, 1, 1L, 1, 1);
        SendResult<Integer, String> result = new SendResult<>(producerRecord, metadata);

        future.set(result);

        when(template.send(isA(ProducerRecord.class))).thenReturn(future);
        // expect
        assertDoesNotThrow( () -> publisher.publishCreate(event).get());
    }
}
