package com.example.vavrweb.user.registration.domain;

import io.vavr.collection.Seq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisteredUserInventory {

    private final UserRepository repository;

    Seq<RegisteredUser> findAll() {
        return repository.findUsers().map(user -> new RegisteredUser(user.getId(), user.getUsername(), user.getEmail()));
    }

}
