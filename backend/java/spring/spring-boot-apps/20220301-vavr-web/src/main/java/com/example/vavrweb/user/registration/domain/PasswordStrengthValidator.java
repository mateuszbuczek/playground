package com.example.vavrweb.user.registration.domain;

import static io.vavr.API.Seq;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

@Component
class PasswordStrengthValidator implements RegistrationValidator {

    @Override
    public Option<Seq<Error>> validate(WebRegistrationApplication registrationApplication) {
        return registrationApplication.getPassword().equals("qwerty") ?
                Option.of(Seq(new Error("password to simple"))) :
                Option.none();
    }
}
