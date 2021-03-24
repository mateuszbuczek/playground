package com.example.linkshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = LinkController.class)
public class LinkControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private LinkService linkService;

    @Test
    public void shortensLink() {
        when(linkService.shortenLink("https://spring.io")).thenReturn(Mono.just("http://localhost:8080/test"));
        client.post()
                .uri("/link")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody("{\"link\": \"https://spring.io\"}")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.shortenedLink")
                .value(val -> assertThat(val).isEqualTo("http://localhost:8080/test"));
    }
}
