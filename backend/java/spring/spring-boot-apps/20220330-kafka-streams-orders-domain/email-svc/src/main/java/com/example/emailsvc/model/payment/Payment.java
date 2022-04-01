package com.example.emailsvc.model.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {

    private String id;
    private String orderId;
    private String ccy;
    private String amount;
}
