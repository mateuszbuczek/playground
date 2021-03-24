package com.example.demo.behavior.visitor;

public class WindowsMailClientVisitor implements MailClientVisitor {

    @Override
    public void visit(OperaMailClient mailClient) {
        System.out.println("Configuration of Opera mail client for Windows complete");
    }

    @Override
    public void visit(SquirrelMailClient mailClient) {
        System.out.println("Configuration of Squirrel mail client for Windows complete");
    }

    @Override
    public void visit(ZimbraMailClient mailClient) {
        System.out.println("Configuration of Zimbra mail client for Windows complete");
    }
}
