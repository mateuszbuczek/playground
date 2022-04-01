package com.example.emailsvc.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

    private String id;
    private String customerId;
    private OrderState state;
    private Product product;
    private Integer quantity;
    private Integer price;
}
