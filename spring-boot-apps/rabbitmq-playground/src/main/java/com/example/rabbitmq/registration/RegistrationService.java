package com.example.rabbitmq.registration;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class RegistrationService {

    @ServiceActivator(inputChannel = "registrationRequest")
    public void register(@Header("dateTime") OffsetDateTime dateTime,
                         @Payload AttendeeRegistrationForm form) {

    }
}
