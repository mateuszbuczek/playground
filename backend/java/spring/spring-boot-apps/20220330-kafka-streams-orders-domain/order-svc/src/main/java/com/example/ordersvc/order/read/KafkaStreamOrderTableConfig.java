package com.example.ordersvc.order.read;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaStreamOrderTableConfig {

    @Value(value = "${topics.orders.name}")
    private String ordersTopicName;

    static final String ORDERS_STORE_NAME = "orders-store";

    @Autowired
    void buildOrderView(StreamsBuilder streamsBuilder) {
        KTable<String, String> table = streamsBuilder.table(ordersTopicName,
                Consumed.with(Serdes.String(), Serdes.String()),
                Materialized.as(ORDERS_STORE_NAME));

        table.toStream().peek((id, order) -> log.info("ORDER IN: saved in data store: {} {}", id, order));

        streamsBuilder.build();
    }
}
