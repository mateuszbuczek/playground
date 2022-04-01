package com.example.vavrweb.user.registration.domain;

import lombok.Value;

@Value
public class RegisteredUser {

    Long id;
    String username;
    String email;
}
