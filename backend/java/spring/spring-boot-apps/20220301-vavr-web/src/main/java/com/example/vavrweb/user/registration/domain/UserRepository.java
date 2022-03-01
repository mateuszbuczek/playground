package com.example.vavrweb.user.registration.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Try;

public interface UserRepository {

    Try<User> tryToRegisterUser(User user);

    Seq<User> findUsers();
}
