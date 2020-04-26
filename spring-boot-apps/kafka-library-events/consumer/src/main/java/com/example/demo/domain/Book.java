package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Book {

    @Id
    private Integer id;
    private String name;
    private String author;
}
