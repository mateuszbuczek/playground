package dev.hateml.accountrabbitmqproducer.infrastructure;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountMessageSender {

    public void sendMessage(RabbitTemplate rabbitTemplate, String accountExchange, String accountRoutingKey, Object accountData) {
        rabbitTemplate.convertAndSend(accountExchange, accountRoutingKey, accountData);
    }
}
