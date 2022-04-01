package com.example.emailsvc;

import com.example.emailsvc.model.OrderEnriched;
import com.example.emailsvc.model.customer.Customer;
import com.example.emailsvc.model.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaStreamEnrichedOrderProducerConfig {

    private final Supplier<GlobalKTable<String, Customer>> customersTable;
    private final Supplier<KStream<String, Order>> ordersStream;

    @Autowired
    void produceEnrichedOrder() {
        JsonSerializer<OrderEnriched> orderSerializer = new JsonSerializer<>();
        JsonDeserializer<OrderEnriched> orderDeserializer = new JsonDeserializer<>(OrderEnriched.class);
        Serde<OrderEnriched> orderEnrichedSerde = Serdes.serdeFrom(orderSerializer, orderDeserializer);

        ordersStream.get().join(
                        customersTable.get(),
                        (orderId, order) -> order.getCustomerId(),
                        (order, customer) -> new OrderEnriched(order.getId(), order.getCustomerId(), customer.getLevel()))
                .peek((key, order) -> log.info("ORDER_CUSTOMER IN: {} {}", key, order.toString()))
                .peek((key, order) -> log.info("ORDER_CUSTOMER OUT: {} {}", key, order.toString()))
                .to(
                        (orderId, orderEnriched, record) -> orderEnriched.getCustomerLevel(),
                        Produced.with(Serdes.String(), orderEnrichedSerde));
    }
}
