package com.example.emailsvc;

import com.example.emailsvc.model.customer.Customer;
import com.example.emailsvc.model.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaStreamTableCustomerConfig {

    @Value(value = "${topics.customers.name}")
    private String customersTopicName;

    @Bean
    Supplier<GlobalKTable<String, Customer>> buildCustomersTable(StreamsBuilder streamsBuilder) {
        JsonSerializer<Customer> customerSerializer = new JsonSerializer<>();
        JsonDeserializer<Customer> customerDeserializer = new JsonDeserializer<>(Customer.class);
        Serde<Customer> customerSerde = Serdes.serdeFrom(customerSerializer, customerDeserializer);

        GlobalKTable<String, Customer> customerGlobalKTable = streamsBuilder.globalTable(customersTopicName,
                Consumed.with(Serdes.String(), customerSerde));

        streamsBuilder.build();

        return () -> customerGlobalKTable;
    }
}
