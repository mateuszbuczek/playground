package com.example.demo.interfaces;

import com.example.demo.domain.Book;
import com.example.demo.domain.LibraryEvent;
import com.example.demo.infrastructure.kafka.LibraryEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
class LibraryEventsControllerUT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibraryEventPublisher publisher;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void postLibraryEvent() throws Exception {
        // given
        Book book = Book.builder()
                .id(123)
                .author("tom")
                .name("asd")
                .build();

        LibraryEvent event = LibraryEvent.builder()
                .book(book)
                .build();

        String value = objectMapper.writeValueAsString(event);

        when(publisher.publish2(event)).thenReturn(null);

        // expect
        mockMvc.perform(
                post("/events")
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postLibraryEvent_4xx() throws Exception {
        // given
        LibraryEvent event = LibraryEvent.builder()
                .book(null)
                .build();

        String value = objectMapper.writeValueAsString(event);

        when(publisher.publish2(event)).thenReturn(null);

        // expect
        mockMvc.perform(
                post("/events")
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("book - must not be null"));
    }
}
