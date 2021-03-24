package com.example.authorizationserver;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class AuthController {

    public static final String AUTH_CODE_SUCCESS_REDIRECT_PATTERN = "redirect:%s?authorization_code=%s";
    public static final String AUTH_TOKEN_SUCCESS_REDIRECT_PATTERN = "redirect:%s?token=%s";

    private final AuthManager authManager;

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("response_type") ResponseType responseType,
            @RequestParam("scope") List<String> scope,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            Model model) {
        // send form data to /oauth/signin
        String scopeStr = scope.toString().replaceAll("[\\[\\]]", "");
        model.addAttribute("response_type", responseType);
        model.addAttribute("scope", scopeStr);
        model.addAttribute("client_id", clientId);
        model.addAttribute("redirect_uri", redirectUri);
        return "login.html";
    }

    @PostMapping(value = "/signin")
    public String signin(@ModelAttribute SignInForm form) {
        if (form.responseType == ResponseType.CODE) {
            String authCode = authManager.createAuthorizationCode(form.getClientId(), form.getUsername(), form.getPassword());
            return String.format(AUTH_CODE_SUCCESS_REDIRECT_PATTERN, form.getRedirectUri(), authCode);
        } else if (form.responseType == ResponseType.TOKEN) {
            String authCode = authManager.createAuthorizationCode(form.getClientId(), form.getUsername(), form.getPassword());
            String token = authManager.exchangeForToken(form.getClientId(), form.getClientSecret(), authCode);
            return String.format(AUTH_TOKEN_SUCCESS_REDIRECT_PATTERN, form.getRedirectUri(), token);
        } else {
            throw new RuntimeException();
        }
    }

    @ResponseBody
    @PostMapping(value = "/token", produces = "application/json")
    public Map<String, String> token(
            @RequestParam("client_id") Long clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("grant_type") GrantType grantType,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    ) {
        String token = null;
        if (grantType == GrantType.AUTHORIZATION_CODE) {
            token = authManager.exchangeForToken(clientId, clientSecret, code);
        } else if (grantType == GrantType.REFRESH_TOKEN) {

        }

        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("access_token", token);
        response.put("token_type", "Bearer");
        return response;
    }

    @ResponseBody
    @PostMapping("/token_info")
    public AuthManager.Token tokenInfo(
            @RequestParam("access_token") String accessToken) {
        return authManager.getTokenInfo(accessToken);
    }

    @ResponseBody
    @PostMapping("/revoke")
    public Map<String, String> revoke(
            @RequestParam("access_token") String accessToken) {
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        return response;
    }

    @RequiredArgsConstructor
    enum ResponseType {
        CODE,
        TOKEN
    }

    @RequiredArgsConstructor
    enum GrantType {
        AUTHORIZATION_CODE,
        REFRESH_TOKEN,
    }

    @Data
    static class SignInForm {
        private @NonNull
        ResponseType responseType;
        private @NonNull
        List<String> scope;
        private @NonNull
        Long clientId;
        private String clientSecret;
        private @NonNull
        String redirectUri;
        private @NonNull
        String username;
        private @NonNull
        String password;
    }
}
