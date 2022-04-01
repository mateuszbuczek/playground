package com.example.emailsvc.model;

import com.example.emailsvc.model.customer.Customer;
import com.example.emailsvc.model.order.Order;
import com.example.emailsvc.model.payment.Payment;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Data
@RequiredArgsConstructor
public class EmailTuple {

    private final Order order;
    private final Payment payment;
    private Customer customer;
}
