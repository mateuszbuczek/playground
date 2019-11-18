package com.example.accountrabbitmqconsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JavaMailSender mailSender;

    String emailReceiver = "TOMSMITH@example.com";

    @RabbitListener(queues = "${account.queue.name}")
    public void handle(final AccountDto accountDto) {
        sendEmail(accountDto);
    }

    private void sendEmail(AccountDto accountDto) {
        MimeMessage mimeMessage =
    }
}
