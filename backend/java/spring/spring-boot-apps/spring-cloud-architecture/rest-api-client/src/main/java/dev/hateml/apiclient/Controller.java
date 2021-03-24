package dev.hateml.apiclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final RestApiClient client;

    @GetMapping("/")
    public String get() {
        return client.getRestApiInstanceId() + "\n" + client.getJsonResponse();
    }
}
