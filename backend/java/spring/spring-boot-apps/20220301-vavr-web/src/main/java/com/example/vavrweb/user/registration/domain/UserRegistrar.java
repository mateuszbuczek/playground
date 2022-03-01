package com.example.vavrweb.user.registration.domain;


import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import java.util.List;

import static io.vavr.API.*;
import static io.vavr.collection.List.ofAll;

@Component
@Transactional
@RequiredArgsConstructor
class UserRegistrar {

    private final UserRepository repository;
    private final List<RegistrationValidator> validators;

    Either<Seq<RegistrationValidator.Error>, RegisteredUser> registerUser(WebRegistrationApplication application) {
        Seq<RegistrationValidator.Error> errors = ofAll(validators)
                .map(validator -> validator.validate(application))
                .flatMap(Option::iterator)
                .flatMap(Traversable::iterator)
                .toList();

        return Match(errors).of(
                Case($(Traversable::isEmpty), () ->
                        repository.tryToRegisterUser(toUser(application))
                                .map(this::toRegisteredUser)
                                .toEither()
                                .mapLeft(ex -> Seq(new RegistrationValidator.Error("error while saving user: " + ex.getMessage())))),
                Case($(), Either.left(errors))
        );

    }

    private User toUser(WebRegistrationApplication application) {
        return User.builder().username(application.getUsername()).build();
    }

    private RegisteredUser toRegisteredUser(User user) {
        return new RegisteredUser(user.getId(), user.getUsername(), user.getEmail());
    }

}
