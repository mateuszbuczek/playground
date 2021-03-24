package com.example.demo.behavior.chainofresponsibility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BillingSupportHandler extends AbstractHandler {

    @Override
    protected void handle(Object object) {
        System.out.println("BillingSupportHandler processing object");
    }
}
