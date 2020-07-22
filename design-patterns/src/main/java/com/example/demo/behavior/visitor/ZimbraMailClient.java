package com.example.demo.behavior.visitor;

public class ZimbraMailClient implements MailClient {

    @Override
    public void sendEmail(String[] mailInfo) {
        System.out.println(" ZimbraMailClient: Sending mail");
    }

    @Override
    public void receiveEmail(String[] mailInfo) {
        System.out.println(" ZimbraMailClient: Receiving mail");
    }

    @Override
    public boolean accept(MailClientVisitor visitor) {
        visitor.visit(this);
        return true;
    }
}
