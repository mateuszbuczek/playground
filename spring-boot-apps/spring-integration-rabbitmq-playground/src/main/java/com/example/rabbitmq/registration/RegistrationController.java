package com.example.rabbitmq.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final MessageChannel messageChannel;

    @PostMapping
    public void register(@Valid @RequestBody AttendeeRegistrationForm form) {
        Message<AttendeeRegistrationForm> message = MessageBuilder.withPayload(form)
                .setHeader("dateTime", OffsetDateTime.now())
                .build();

        messageChannel.send(message);
    }
}
