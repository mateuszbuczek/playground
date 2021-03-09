package com.example.authorizationserver;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Users {
    private final InMemoryRepository<String, UserDetails> users = new InMemoryRepository<>();

    public void create(String username, String password, String name) {
        UserDetails userDetails = new UserDetails(name, password);
        users.put(username, userDetails);
    }

    public Optional<UserDetails> get(String username, String password) {
        return users.get(username).filter(user -> password.equals(user.getPassword()));
    }

    public Optional<UserDetails> getAuthenticated(String username) {
        return users.get(username);
    }

    @Value
    @EqualsAndHashCode
    static class UserDetails {
        String name;
        String password;
    }
}
