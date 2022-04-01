package com.example.emailsvc;

import com.example.emailsvc.model.EmailTuple;
import com.example.emailsvc.model.customer.Customer;
import com.example.emailsvc.model.order.Order;
import com.example.emailsvc.model.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaStreamEmailProcessor {

    private final Supplier<KStream<String, Order>> ordersStream;
    private final Supplier<KStream<String, Payment>> paymentsStream;
    private final Supplier<GlobalKTable<String, Customer>> customersTable;

    @Autowired
    void emailProcessor() {
        JsonSerializer<Order> orderSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> orderDeserializer = new JsonDeserializer<>(Order.class);
        Serde<Order> orderSerde = Serdes.serdeFrom(orderSerializer, orderDeserializer);
        JsonSerializer<Payment> paymentSerializer = new JsonSerializer<>();
        JsonDeserializer<Payment> paymentDeserializer = new JsonDeserializer<>(Payment.class);
        Serde<Payment> paymentSerde = Serdes.serdeFrom(paymentSerializer, paymentDeserializer);

        StreamJoined<String, Order, Payment> orderPaymentSerde = StreamJoined.with(Serdes.String(), orderSerde, paymentSerde);

        KStream<String, EmailTuple> orderPaymentStream = ordersStream.get().join(
                        paymentsStream.get(),
                        EmailTuple::new,
                        JoinWindows.of(Duration.ofMinutes(1)),
                        orderPaymentSerde
                )
                .peek((key, order) -> log.info("ORDER_PAYMENT IN: {} {}", key, order.toString()));


        orderPaymentStream.join(
                        customersTable.get(),
                        (key, emailTuple) -> emailTuple.getOrder().getCustomerId(),
                        (emailTuple, customer) -> {
                            emailTuple.setCustomer(customer);
                            return emailTuple;
                        })
                .peek((key, order) -> log.info("ORDER_PAYMENT_CUSTOMER IN: {} {}", key, order.toString()))
                .peek(sendEmail());
    }

    private ForeachAction<String, EmailTuple> sendEmail() {
        return (key, emailTuple) -> log.info("sending email: {} {}", key, emailTuple);
    }
}
