package com.hateml.users.api;

import com.hateml.users.api.user.UserDto;
import com.hateml.users.api.user.UserDtoFactory;
import com.hateml.users.boundary.Users;
import com.hateml.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Users users;
    private final UserDtoFactory userDtoFactory;
    private final Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "working on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user) {

        UserDto userDto = userDtoFactory.create(users.createUser(user));

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}
