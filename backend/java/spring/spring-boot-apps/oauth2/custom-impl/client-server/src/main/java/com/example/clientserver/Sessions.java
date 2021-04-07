package com.example.clientserver;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Sessions {

    private final Map<String, String> sessionTokenMap = new ConcurrentHashMap<>();

    String associateTokenWithSession(String access_token) {
        String sessionId = UUID.randomUUID().toString();
        sessionTokenMap.put(sessionId, access_token);
        return sessionId;
    }

    public void validate(String value) {
        if (!sessionTokenMap.containsKey(value)) {
            throw new RuntimeException("no session found");
        }
    }
}
