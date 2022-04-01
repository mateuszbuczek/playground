package com.example.emailsvc;

import com.example.emailsvc.model.payment.Payment;
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
public class KafkaStreamPaymentConfig {

    @Value(value = "${topics.payments.name}")
    private String paymentsTopicName;

    @Bean
    Supplier<KStream<String, Payment>> buildPaymentsStream(StreamsBuilder streamsBuilder) {
        JsonSerializer<Payment> paymentSerializer = new JsonSerializer<>();
        JsonDeserializer<Payment> paymentDeserializer = new JsonDeserializer<>(Payment.class);
        Serde<Payment> paymentSerde = Serdes.serdeFrom(paymentSerializer, paymentDeserializer);

        KStream<String, Payment> paymentsStream = streamsBuilder.stream(paymentsTopicName,
                        Consumed.with(Serdes.String(), paymentSerde))
                .selectKey((key, payment) -> payment.getOrderId())
                .peek((key, payment) -> log.info("PAYMENT IN: {} {}", key, payment.toString()));

        streamsBuilder.build();

        return () -> paymentsStream;
    }

}
