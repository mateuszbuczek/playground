package com.example.ordersvc.order.create;

import com.example.ordersvc.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderSubmitController {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Value(value = "${topics.orders.name}")
    private String ordersTopicName;

    @PostMapping
    public void submitOrder(@RequestBody Order order) throws ExecutionException, InterruptedException, TimeoutException {
        SendResult<String, Order> result = kafkaTemplate
                .send(ordersTopicName, order.getId(), order)
                .get(2000, TimeUnit.SECONDS);

        log.info("ORDER OUT: order submitted {}", result.toString());
    }
}
