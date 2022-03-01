package com.example.vavrweb.user.registration.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

@Component
class UniqueEmailValidator implements RegistrationValidator {

    @Override
    public Option<Seq<Error>> validate(WebRegistrationApplication registrationApplication) {
        return Option.none();
    }
}
