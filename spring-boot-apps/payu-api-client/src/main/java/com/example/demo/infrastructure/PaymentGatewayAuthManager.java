package com.example.demo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PaymentGatewayAuthManager {

  private final PaymentGatewayAuthClient client;
  private final Object lock = new Object();

  private Token token = new Token(null, null);

  public String getAuthToken() {
    if (token.isInvalid()) {
      synchronized (lock) {
        if (token.isInvalid()) {
          client
              .makeCallForToken()
              .retry(2)
              //TODO remove blocking call
              .blockOptional()
            .ifPresent(result -> token = result);
        }
      }
    }

    return token.getValue();
  }

  @Getter
  @AllArgsConstructor
  static class Token {
    private final String value;
    private final Instant expiresIn;

    boolean isInvalid() {
      return this.value == null
              || this.expiresIn == null
              || !this.expiresIn.isAfter(Instant.now().plusSeconds(60));
    }
  }
}
