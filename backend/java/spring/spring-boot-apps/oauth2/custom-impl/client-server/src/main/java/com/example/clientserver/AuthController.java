package com.example.clientserver;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private static final String OAUTH_AUTHORIZE_PATTERN =
            "redirect:%s/oauth/authorize?redirect_uri=%s&client_id=%s&scope=openid&response_type=CODE";
    public static final String OAUTH_TOKEN_URL_PATTERN = "%s/oauth/token?client_id=%s&client_secret=%s&grant_type=AUTHORIZATION_CODE&redirect_uri=any&code=%s";

    private final OauthProperties oauthProperties;
    private final Sessions sessions;

    @GetMapping
    public String loginPage(HttpServletRequest request) {
        Optional<Cookie> sessionCookie = getSessionCookie(request);
        if (sessionCookie.isPresent()) {
            sessions.validate(sessionCookie.get().getValue());
            return "redirect:/success";
        } else {
            return "login.html";
        }
    }

    private Optional<Cookie> getSessionCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("SESSION"))
                .findFirst();
    }

    @GetMapping("/success")
    @ResponseBody
    public String success(HttpServletRequest request) {
        String sessionValue = getSessionCookie(request).orElseThrow().getValue();
        sessions.validate(sessionValue);
        return sessionValue;
    }

    @GetMapping("/oauth_redirect")
    public String oauthRedirect() {
        return String.format(OAUTH_AUTHORIZE_PATTERN,
                oauthProperties.getAuthServerUrl(),
                oauthProperties.getRedirectUrl(),
                oauthProperties.getClientId());
    }

    @GetMapping("/oauth")
    public String loginOauth(@RequestParam("authorization_code") String code, HttpServletResponse httpResponse) {
        ResponseEntity<Map> response = new RestTemplate()
                .postForEntity(String.format(OAUTH_TOKEN_URL_PATTERN,
                        oauthProperties.getAuthServerUrl(),
                        oauthProperties.getClientId(),
                        oauthProperties.getClientSecret(),
                        code), null, Map.class);

        String session = sessions.associateTokenWithSession((String) response.getBody().get("access_token"));
        httpResponse.addCookie(new Cookie("SESSION", session));
        return "redirect:/";
    }
}