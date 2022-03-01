package com.example.vavrweb.user.registration.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;
import static io.vavr.API.$;
import static io.vavr.API.Seq;
import static io.vavr.Predicates.instanceOf;

@Service
@RequiredArgsConstructor
public class RegistrationFacade {

    private final UserRegistrar userRegistrar;
    private final RegisteredUserInventory inventory;

    public Either<Seq<RegistrationValidator.Error>, RegisteredUser> create(RegistrationApplication application) {
        return Match(application).of(
                Case($(instanceOf(WebRegistrationApplication.class)),userRegistrar::registerUser),
                Case($(), Either.left(Seq(new RegistrationValidator.Error("unsupported type: " + application.getClass())))));
    }

    public Seq<RegisteredUser> findAll() {
        return inventory.findAll();
    }
}
