package com.example.demo.interfaces;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.infrastructure.kafka.LibraryEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LibraryEventsController {

    private final LibraryEventPublisher publisher;

    @PostMapping("/events")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody @Valid LibraryEvent event) throws JsonProcessingException {

        publisher.publishCreate(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @PutMapping("/events")
    public ResponseEntity<?> puttLibraryEvent(@RequestBody @Valid LibraryEvent event) throws JsonProcessingException {

        if (event.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please pass id");
        }

        publisher.publishUpdate(event);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }
}
