package com.example.demo.behavior.visitor;

public class SquirrelMailClient implements MailClient {

    @Override
    public void sendEmail(String[] mailInfo) {
        System.out.println(" SquirrelMailClient: Sending mail");
    }

    @Override
    public void receiveEmail(String[] mailInfo) {
        System.out.println(" SquirrelMailClient: Receiving mail");
    }

    @Override
    public boolean accept(MailClientVisitor visitor) {
        visitor.visit(this);
        return true;
    }
}
