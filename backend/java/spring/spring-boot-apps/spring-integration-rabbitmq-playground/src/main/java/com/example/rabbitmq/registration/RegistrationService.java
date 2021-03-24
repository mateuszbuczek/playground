package com.example.rabbitmq.registration;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class RegistrationService {

    public void register(@Header("dateTime") OffsetDateTime dateTime,
                         @Payload AttendeeRegistrationForm form) {

    }
}
