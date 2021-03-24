package com.example.demo.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "integration.payment-gateway")
@Getter
@Setter
public class PaymentGatewayProperties {

    private String url;
    private Integer clientId;
    private String clientSecret;
    private String shopId;
}
