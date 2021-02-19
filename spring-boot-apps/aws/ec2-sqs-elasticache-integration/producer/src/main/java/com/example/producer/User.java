package com.example.producer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

    private Integer id;
    private String name;
    private String salary;
}
