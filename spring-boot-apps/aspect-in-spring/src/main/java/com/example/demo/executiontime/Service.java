package com.example.demo.executiontime;

import com.example.demo.executiontime.Logged;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Service {

    @Logged
    public void throwException() {
        throw new RuntimeException("Custom-Exception");
    }

    @Logged
    public String randomString() {
        return IntStream.range(0, 10)
                .mapToObj(n -> (char) n)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
