package com.example.ordersvc.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    private String id;
    private String customerId;
    private OrderState state;
    private Product product;
    private Integer quantity;
    private Integer price;
}
