package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class LibraryEvent {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private LibraryEventType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Book book;

    public void markAsNew() {
        this.type = LibraryEventType.NEW;
    }

    public void markAsUpdate() {
        this.type = LibraryEventType.UPDATE;
    }
}
