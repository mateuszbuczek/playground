package com.example.demo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentGatewayClient {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String GET_SHOP_DETAILS_PATTERN = "%s/shops/%s";

    private final PaymentGatewayProperties properties;
    private final PaymentGatewayAuthManager authManager;

    public Mono<Object> getShopDetails() {
        String url = String.format(GET_SHOP_DETAILS_PATTERN, properties.getUrl(), properties.getShopId());
        return WebClient.create()
                .get()
                .uri(url)
                .header(AUTH_HEADER_KEY, authManager.getAuthToken())
                .retrieve()
                .bodyToMono(Object.class);
    }
}
