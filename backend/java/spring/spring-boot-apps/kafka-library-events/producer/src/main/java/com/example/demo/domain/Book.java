package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public class Book {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String author;
}
