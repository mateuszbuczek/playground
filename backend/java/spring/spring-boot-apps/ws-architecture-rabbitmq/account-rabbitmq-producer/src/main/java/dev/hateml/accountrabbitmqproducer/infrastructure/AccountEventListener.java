package dev.hateml.accountrabbitmqproducer.infrastructure;

import dev.hateml.accountrabbitmqproducer.domain.account.AccountEvent;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {

    private RabbitTemplate rabbitTemplate;
    private Exchange exchange;
    private AccountMessageSender accountMessageSender;

    @Value("${account.exchange.name}")
    private String accountExchangeName;

    @Value("${account.queue.name}")
    private String accountQueueName;

    @Value("${account.routing.key}")
    private String accountRoutingKey;

    public AccountEventListener(RabbitTemplate rabbitTemplate, Exchange exchange, AccountMessageSender accountMessageSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.accountMessageSender = accountMessageSender;
    }

    @EventListener
    public void onApplicationEvent(AccountEvent accountEvent) {
        System.out.println("Received Account Event " + accountEvent.getEventType());
        System.out.println("Received Account " + accountEvent.getAccountDto().toString());

        accountMessageSender.sendMessage(rabbitTemplate, accountExchangeName, accountRoutingKey, accountEvent.getAccountDto());
    }
}
