package com.example.authorizationserver;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T, R> {
    private Map<T, R> data = new ConcurrentHashMap<>();

    R get(T key) {
        return data.get(key);
    }

    void put(T key, R val) {
        data.put(key, val);
    }

    Collection<R> values() {
        return data.values();
    }
}
