package com.example.linkshortener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RedisLinkRepository implements LinkRepository {

    private final ReactiveRedisOperations<String, String> reactiveRedisOperations;

    @Override
    public Mono<Link> save(Link link) {
        return reactiveRedisOperations.opsForValue()
                .set(link.getKey(), link.getOriginalLink())
                .map(__ -> link);
    }

    @Override
    public Mono<Link> findByKey(String key) {
        return reactiveRedisOperations.opsForValue()
                .get(key)
                .map(result -> new Link(result, key));
    }
}
