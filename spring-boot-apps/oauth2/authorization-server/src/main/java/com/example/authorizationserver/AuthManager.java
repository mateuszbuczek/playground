package com.example.authorizationserver;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManager {
    private final Users users;
    private final Clients clients;
    private final TokenService tokenService;

    public String createAuthorizationCode(Long clientId, String username, String password) {
        users.get(username, password).orElseThrow();
        Clients.Client client = clients.get(clientId).orElseThrow();

        return client.createAuthorizationCode(username);
    }

    public String exchangeForToken(Long clientId, String clientSecret, String code) {
        String username = clients.get(clientId).orElseThrow()
                .getUsername(code, clientSecret).orElseThrow();

        Users.UserDetails userDetails = users.getAuthenticated(username).orElseThrow();

        Token token = new Token(userDetails.getName(), username);
        return tokenService.createToken(token);
    }

    public Token getTokenInfo(String accessToken) {
        return tokenService.getFromToken(accessToken, Token.class);
    }

    @Value
    static class Token {
        String name;
        String username;
    }
}
