package com.example.demo.behavior.visitor;

public class OperaMailClient implements MailClient {

    @Override
    public void sendEmail(String[] mailInfo) {
        System.out.println(" OperaMailClient: Sending mail");
    }

    @Override
    public void receiveEmail(String[] mailInfo) {
        System.out.println(" OperaMailClient: Receiving mail");
    }

    @Override
    public boolean accept(MailClientVisitor visitor) {
        visitor.visit(this);
        return true;
    }
}
