package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    private Integer id;
    private String name;
    private String salary;
}
