package com.example.demo.behavior.chainofresponsibility;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class TechnicalSupportHandler extends AbstractHandler {

    @Override
    protected void handle(Object object) {
        System.out.println("TechnicalSupportHandler processing object");
    }
}
