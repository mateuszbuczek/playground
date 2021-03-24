package com.example.authorizationserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Component
public class Clients {
    private final InMemoryRepository<Long, Client> clients = new InMemoryRepository<>();

    public Client create() {
        long id = new Random().nextLong();
        String secret = UUID.randomUUID().toString();
        Client client = new Client(id, secret);
        clients.put(id, client);
        return client;
    }

    public Optional<Client> get(Long clientId) {
        return clients.get(clientId);
    }

    @Value
    static class Client {
        @JsonIgnore
        InMemoryRepository<String, String> authorizationCodesUsers = new InMemoryRepository<>();

        Long clientId;
        String secret;

        public String createAuthorizationCode(String username) {
            String authCode = UUID.randomUUID().toString();
            authorizationCodesUsers.put(authCode, username);
            return authCode;
        }

        public Optional<String> getUsername(String authCode, String secret) {
            if (!this.secret.equals(secret)) {
                throw new RuntimeException();
            }
            return authorizationCodesUsers.get(authCode);
        }
    }
}
