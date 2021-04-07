package com.example.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> String createToken(T object) {
        String serializedObject = objectMapper.writeValueAsString(object);
        return Base64.getEncoder().encodeToString(serializedObject.getBytes(StandardCharsets.UTF_8));
    }

    @SneakyThrows
    public <T> T getFromToken(String token, Class<T> clazz) {
        String decodedToken = new String(Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));
        return objectMapper.readValue(decodedToken, clazz);
    }
}
