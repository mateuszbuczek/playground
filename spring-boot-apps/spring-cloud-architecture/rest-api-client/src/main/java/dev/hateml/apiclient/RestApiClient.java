package dev.hateml.apiclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "rest-api")
public interface RestApiClient {

    @GetMapping("/")
    public String getRestApiInstanceId();

    @GetMapping("/jsonResponse")
    public String getJsonResponse();
}
