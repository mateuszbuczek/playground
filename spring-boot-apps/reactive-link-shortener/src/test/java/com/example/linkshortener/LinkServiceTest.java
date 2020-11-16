package com.example.linkshortener;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinkServiceTest {

    private static LinkRepository linkRepository = mock(LinkRepository.class);
    private LinkService linkService = new LinkService("http://some-domain.com", linkRepository);

    @BeforeAll
    public static void setup() {
     when(linkRepository.save(any())).thenAnswer((Answer<Mono<Link>>) invocationOnMock -> {
         Link link = (Link) invocationOnMock.getArguments()[0];
         return Mono.just(link);
     });
    }

    @Test
    public void shortensLink() {
        StepVerifier.create(linkService.shortenLink("http://spring.io"))
                .expectNextMatches(result -> result != null && result.length() > 0
                    && result.startsWith("http://some-domain.com"))
                .expectComplete()
                .verify();
    }
}
