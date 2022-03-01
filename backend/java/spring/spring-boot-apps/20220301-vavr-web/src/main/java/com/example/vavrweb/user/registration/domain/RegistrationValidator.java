package com.example.vavrweb.user.registration.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import lombok.Data;

public interface RegistrationValidator {

    Option<Seq<Error>> validate(WebRegistrationApplication registrationApplication);

    @Data
    class Error {
        private final String message;
    }
}
