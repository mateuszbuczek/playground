package com.example.demo.behavior.chainofresponsibility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientContactSupportHandler extends AbstractHandler {

    @Override
    protected void handle(Object object) {
        System.out.println("ClientContactSupportHandler processing object");
    }
}
