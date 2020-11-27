package com.example.demo.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PaymentGatewayAuthClient {

  private static final String GET_TOKEN_PATTERN =
      "%s/pl/standard/user/oauth/authorize?grant_type=client_credentials&client_id=%s&client_secret=%s";

  private final PaymentGatewayProperties properties;

  public Mono<PaymentGatewayAuthManager.Token> makeCallForToken() {
    String url =
        String.format(
            GET_TOKEN_PATTERN,
            properties.getUrl(),
            properties.getClientId(),
            properties.getClientSecret());
    return WebClient.create()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(Request.class)
        .map(
            request ->
                new PaymentGatewayAuthManager.Token(
                    request.getAccessToken(), Instant.now().plusSeconds(request.getExpiresIn())));
  }

  @Data
  class Request {
    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("access_token")
    private String accessToken;
  }
}
