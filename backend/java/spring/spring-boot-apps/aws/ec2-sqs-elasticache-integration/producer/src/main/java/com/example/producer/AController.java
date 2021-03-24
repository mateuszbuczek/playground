package com.example.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AController {

    private final SqsSender sender;

    @PostMapping
    public User create(@RequestBody User user) {
        sender.send(user);

        return user;
    }
}
