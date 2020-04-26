package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public class LibraryEvent {

    private Integer id;
    private LibraryEventType type;

    @NotNull
    @Valid
    private Book book;

    public void markAsNew() {
        this.type = LibraryEventType.NEW;

    }
    public void markAsUpdate() {
        this.type = LibraryEventType.UPDATE;
    }
}
