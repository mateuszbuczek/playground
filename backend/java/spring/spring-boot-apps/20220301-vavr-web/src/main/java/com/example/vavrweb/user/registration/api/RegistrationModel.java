package com.example.vavrweb.user.registration.api;

import com.example.vavrweb.user.registration.domain.RegistrationApplication;
import com.example.vavrweb.user.registration.domain.WebRegistrationApplication;
import lombok.Value;

@Value
public class RegistrationModel {

    String username;
    String password;
    String email;

    public RegistrationApplication toRegistrationApplication() {
        return new WebRegistrationApplication(this.username, this.password, this.email);
    }
}
