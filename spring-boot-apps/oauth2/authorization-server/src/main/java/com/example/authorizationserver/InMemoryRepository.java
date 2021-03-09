package com.example.authorizationserver;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T, R> {
    private Map<T, R> data = new ConcurrentHashMap<>();

    Optional<R> get(T key) {
        R r = data.get(key);
        return r == null ? Optional.empty() : Optional.of(r);
    }

    void put(T key, R val) {
        data.put(key, val);
    }

    Collection<R> values() {
        return data.values();
    }
}
