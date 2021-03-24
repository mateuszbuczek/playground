package com.example.demo.behavior.visitor;

public class Main {

    public static void main(String[] args) {
        MailClientVisitor macVisitor=new MacMailClientVisitor();
        MailClientVisitor linuxVisitor=new  LinuxMailClientVisitor();
        MailClientVisitor windowsVisitor=new WindowsMailClientVisitor();
        MailClient operaMailClient = new OperaMailClient();
        MailClient squirrelMailClient=new SquirrelMailClient();
        MailClient zimbraMailClient=new ZimbraMailClient();

        operaMailClient.accept(macVisitor);
        operaMailClient.accept(linuxVisitor);
        operaMailClient.accept(windowsVisitor);
    }
}
