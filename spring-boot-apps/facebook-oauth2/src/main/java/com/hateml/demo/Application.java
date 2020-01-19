package com.hateml.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/api")
    public String get(Principal principal) {
        Map<String, Object> details = (Map<String, Object>)
                ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
        String username = (String) details.get("name");
        return "ACCESSED - " + username;
    }
}
