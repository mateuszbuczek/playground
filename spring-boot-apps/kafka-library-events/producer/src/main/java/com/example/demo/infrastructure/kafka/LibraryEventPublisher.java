package com.example.demo.infrastructure.kafka;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.domain.LibraryEventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryEventPublisher {

    private final KafkaTemplate<Integer, String> template;
    private final ObjectMapper mapper;

    public void publish(LibraryEvent event) throws JsonProcessingException {
        Integer id = event.getId();
        String payload = mapper.writeValueAsString(event);
        event.markAsNew();

        ListenableFuture<SendResult<Integer, String>> listenableFuture = template.sendDefault(id, payload);

        listenableFuture.addCallback(getCallback());
    }

    public ListenableFuture<SendResult<Integer, String>> publishCreate(LibraryEvent event) throws JsonProcessingException {
        Integer id = event.getId();
        String payload = mapper.writeValueAsString(event);
        event.markAsNew();

//        custom headers
        List<Header> headers = List.of(
                new RecordHeader("event-source", "scanner".getBytes()),
                new RecordHeader("event-type", LibraryEventType.NEW.toString().getBytes())
        );
        ProducerRecord<Integer, String> message = new ProducerRecord<>("library-events", null, id, payload, headers);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = template.send(message);
        listenableFuture.addCallback(getCallback());

        return listenableFuture;
    }

    public ListenableFuture<SendResult<Integer, String>> publishUpdate(LibraryEvent event) throws JsonProcessingException {
        Integer id = event.getId();
        String payload = mapper.writeValueAsString(event);
        event.markAsUpdate();

//        custom headers
        List<Header> headers = List.of(
                new RecordHeader("event-source", "scanner".getBytes()),
                new RecordHeader("event-type", LibraryEventType.UPDATE.toString().getBytes())
        );
        ProducerRecord<Integer, String> message = new ProducerRecord<>("library-events", null, id, payload, headers);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = template.send(message);
        listenableFuture.addCallback(getCallback());

        return listenableFuture;
    }

    private ListenableFutureCallback<SendResult<Integer, String>> getCallback() {
        return new ListenableFutureCallback<>() {

            @SneakyThrows
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error occurred during sending message: {}", ex.getMessage());
                throw ex;
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("Message with key '{}' sent and acknowledged by quorum", result.getProducerRecord().key());
            }
        };
    }
}
