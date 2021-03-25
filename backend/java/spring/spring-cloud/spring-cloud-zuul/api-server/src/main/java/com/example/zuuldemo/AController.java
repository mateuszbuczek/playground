package com.example.zuuldemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/users")
public class AController {

    @GetMapping
    public List<String> getUsers(HttpServletRequest request) {
        return IntStream.range(0, 10)
                .boxed()
                .map(__ -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id) {
        return UUID.randomUUID().toString() + " " + id;
    }
}
