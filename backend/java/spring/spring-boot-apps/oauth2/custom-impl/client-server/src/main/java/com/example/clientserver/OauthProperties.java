package com.example.clientserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.oauth")
@Getter
@Setter
class OauthProperties {
    private String clientId;
    private String clientSecret;
    private String authServerUrl;
    private String redirectUrl;
}
