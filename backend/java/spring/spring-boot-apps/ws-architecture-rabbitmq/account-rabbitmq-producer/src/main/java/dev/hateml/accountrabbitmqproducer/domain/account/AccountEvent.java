package dev.hateml.accountrabbitmqproducer.domain.account;

import dev.hateml.accountrabbitmqproducer.interfaces.AccountDto;
import org.springframework.context.ApplicationEvent;

public class AccountEvent extends ApplicationEvent {

    private String eventType;
    private AccountDto accountDto;

    public AccountEvent(Object source, String eventType, AccountDto accountDto) {
        super(source);
        this.eventType = eventType;
        this.accountDto = accountDto;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    @Override
    public String toString() {
        return "AccountEvent{" +
                "evenType='" + eventType + '\'' +
                ", accountDto=" + accountDto +
                '}';
    }
}
