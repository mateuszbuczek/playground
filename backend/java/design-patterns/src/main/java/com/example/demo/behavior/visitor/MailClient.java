package com.example.demo.behavior.visitor;

public interface MailClient {

    void sendEmail(String[] mailInfo);
    void receiveEmail(String[] mailInfo);
    boolean accept(MailClientVisitor visitor);
}
