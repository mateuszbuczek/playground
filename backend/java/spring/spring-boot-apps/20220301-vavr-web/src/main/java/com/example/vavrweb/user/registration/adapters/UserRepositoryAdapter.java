package com.example.vavrweb.user.registration.adapters;

import com.example.vavrweb.user.registration.domain.User;
import com.example.vavrweb.user.registration.domain.UserRepository;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.vavr.collection.List.ofAll;

@Component
@RequiredArgsConstructor
class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository userRepository;

    @Override
    public Try<User> tryToRegisterUser(User user) {
        return Try.of(() -> {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new RuntimeException("already exists");
            }
            return userRepository.save(user);
        });
    }

    @Override
    public Seq<User> findUsers() {
        return ofAll(userRepository.findAll());
    }
}
