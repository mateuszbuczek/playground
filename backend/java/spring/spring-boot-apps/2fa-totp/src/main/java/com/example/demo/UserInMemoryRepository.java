package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserInMemoryRepository {

    // accountName:secretKey
    private static final Map<String, String> USERS = new ConcurrentHashMap<>();

    public void save(String accountName, String key) {
        USERS.putIfAbsent(accountName, key);
    }

    public String getSecretKey(String accountName) {
        return USERS.get(accountName);
    }
}
