package com.example.vavrweb.user.registration.domain;

import lombok.Value;

@Value
public class WebRegistrationApplication implements RegistrationApplication {

    String username;
    String password;
    String email;
}
