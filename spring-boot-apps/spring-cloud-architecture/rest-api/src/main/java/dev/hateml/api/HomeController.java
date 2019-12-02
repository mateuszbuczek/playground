package dev.hateml.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${app.randomId}")
    private String randomId;

    @GetMapping("/")
    public String hello() {
        return "This application id is = " + randomId;
    }

    @GetMapping("/jsonResponse")
    public String getJson() {
        return "{\"message\": \"successfulGet\"}";
    }
}
