package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.ToString;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@AllArgsConstructor
@ToString
public class User {

    private String username;
    private String password;

    static User createRandom() {
        return new User(randomAlphabetic(10), randomAlphabetic(10));
    }
}
