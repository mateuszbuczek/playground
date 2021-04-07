package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AController {

    private final OAuth2AuthorizedClientService clientService;

    @GetMapping("/portfolio")
    public String handlePortfolio() {
        return "main.html";
    }

    /**
     * Used to perform requests on a resource server.
     * If provider does not handle openid, it can be also used as identity provider.
     */
    @GetMapping("/me/access_token")
    @ResponseBody
    public String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
            accessToken = client.getAccessToken().getTokenValue();
        }
        return accessToken;
    }


    @GetMapping("/me/refresh_token")
    @ResponseBody
    public String getRefreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String refreshToken = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
            OAuth2RefreshToken token = client.getRefreshToken();
            if (token != null) {
                refreshToken = token.getTokenValue();
            }
        }
        return refreshToken;
    }

    /**
     * Providers that support openid and return openid are mapped to OidcUser authentication.
     * Such user should have the same fields filled no matter provider was used.
     * Subject in such object should be unique within provider.
     * facebook does not support openid now
     */
    @GetMapping("/me/openid_token")
    @ResponseBody
    public String getOpenIdToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String openidToken = null;
        if (authentication.getClass().isAssignableFrom(OidcUser.class)) {
            OidcUser oidcUser = (OidcUser) authentication;
            openidToken = oidcUser.getIdToken().getTokenValue();
        }
        return openidToken;
    }

    @GetMapping("/me/provider")
    @ResponseBody
    public String getProvider() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String provider = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            provider = oauthToken.getAuthorizedClientRegistrationId();
        }
        return provider;
    }
}
