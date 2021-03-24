package com.example.linkshortener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService service;

    @PostMapping("/link")
    Mono<CreateLinkResponse> create(@RequestBody CreateLinkRequest request) {
        return service.shortenLink(request.getLink())
                .map(CreateLinkResponse::new);
    }

    @Data
    private static class CreateLinkRequest {
        String link;
    }

    @Data
    @AllArgsConstructor
    private static class CreateLinkResponse {
        String shortenedLink;
    }
}
