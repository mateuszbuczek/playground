package com.example.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/oauth")
public class AuthController {

    public static final String AUTH_SUCCESS_REDIRECT_PATTERN = "redirect:%s?authorization_code=%s";

    private final InMemoryRepository<Long, String> userAuthorizationCode = new InMemoryRepository<>();
    private final InMemoryRepository<Long, String> userAccessToken = new InMemoryRepository<>();

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("response_type") ResponseType responseType,
            @RequestParam("scope") List<String> scope,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri
    ) {
        // send form to the client, authenticate, and get user id
        Long userId = 1L;
        String authCode = UUID.randomUUID().toString();

        userAuthorizationCode.put(userId, authCode);
        return String.format(AUTH_SUCCESS_REDIRECT_PATTERN, redirectUri, authCode);
    }

    @ResponseBody
    @PostMapping(value = "/token", produces = "application/json")
    public Map<String, String> token(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") GrantType grantType,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    ) {
        Long userId = 1L;
        userAuthorizationCode.values().stream().filter(code::equals).findFirst().orElseThrow();

        String accessToken = UUID.randomUUID().toString();
        userAccessToken.put(userId, accessToken);

        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("access_token", accessToken);
        response.put("token_type", "Bearer");
        return response;
    }

    @ResponseBody
    @PostMapping("/token_info")
    public Map<String, String> tokenInfo(
            @RequestParam("access_token") String accessToken) {
        userAccessToken.values().stream().filter(accessToken::equals).findFirst()
                .orElseThrow();

        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("user_id", "1");
        return response;
    }

    @ResponseBody
    @PostMapping("/revoke")
    public Map<String, String> revoke(
            @RequestParam("access_token") String accessToken) {
        userAccessToken.values().stream().filter(accessToken::equals).findFirst()
                .orElseThrow();

        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("user_id", "1");
        return response;
    }

    @RequiredArgsConstructor
    enum ResponseType {
        CODE
    }

    @RequiredArgsConstructor
    enum GrantType {
        AUTHORIZATION_CODE,
        REFRESH_TOKEN,
    }

}
