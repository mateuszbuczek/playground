package com.example.demo.behavior.chainofresponsibility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class AbstractHandler {

    private AbstractHandler nextHandler;

    public AbstractHandler setNextHandler(AbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this;
    }

    public void process(Object object) {
        this.handle(object);
        if (nextHandler != null) {
            nextHandler.process(object);
        }
    }

    protected abstract void handle(Object object);
}
