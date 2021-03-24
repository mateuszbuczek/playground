package com.example.demo.behavior.chainofresponsibility;

public class Main {

    public static void main(String[] args) {
        getChainOfResponsibility().process(new Object());
    }

    private static AbstractHandler getChainOfResponsibility() {
        BillingSupportHandler billingSupportHandler = new BillingSupportHandler();
        ClientContactSupportHandler clientContactSupportHandler = (ClientContactSupportHandler) new ClientContactSupportHandler().setNextHandler(billingSupportHandler);
        return new TechnicalSupportHandler().setNextHandler(clientContactSupportHandler);
    }
}
