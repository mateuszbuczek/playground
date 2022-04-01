package com.example.emailsvc.model;

import lombok.Data;

@Data
public class OrderEnriched {

    private final String id;
    private final String customerId;
    private final String customerLevel;
}
