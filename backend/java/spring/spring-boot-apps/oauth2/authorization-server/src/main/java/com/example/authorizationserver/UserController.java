package com.example.authorizationserver;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final Users users;

    @PostMapping
    public UserSignUpForm register(@RequestBody UserSignUpForm form) {
        users.create(form.getUsername(), form.getPassword(), form.getName());
        return form;
    }

    @Value
    static class UserSignUpForm {
        @NonNull String name;
        @NonNull String username;
        @NonNull String password;
    }
}
