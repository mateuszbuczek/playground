package com.example.linkshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisLinkRepositoryTest {

    @Autowired
    private RedisLinkRepository repository;

    @Test
    public void returnSameLinkAsArgument() {
        Link link = new Link("http://spring.io", "abc222");
        StepVerifier.create(repository.save(link))
                .expectNext(link)
                .verifyComplete();
    }

    @Test
    public void savesInRedis() {
        Link link = new Link("http://spring.io", "abc222");
        StepVerifier.create(repository.save(link)
                    .flatMap(__ -> repository.findByKey(link.getKey()))
                )
                .expectNext(link)
                .verifyComplete();
    }
}