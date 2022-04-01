package com.example.emailsvc;

import com.example.emailsvc.model.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.function.Supplier;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaStreamOrderConfig {

    @Value(value = "${topics.orders.name}")
    private String ordersTopicName;

    @Bean
    Supplier<KStream<String, Order>> buildOrderStream(StreamsBuilder streamsBuilder) {
        JsonSerializer<Order> orderSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> orderDeserializer = new JsonDeserializer<>(Order.class);
        Serde<Order> orderSerde = Serdes.serdeFrom(orderSerializer, orderDeserializer);

        KStream<String, Order> ordersStream = streamsBuilder.stream(ordersTopicName,
                        Consumed.with(Serdes.String(), orderSerde))
                .peek((key, order) -> log.info("ORDER IN: {} {}", key, order.toString()));

        streamsBuilder.build();

        return () -> ordersStream;
    }
}
