package com.example.demo.behavior.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarStopCommand implements CommandBase {

    private final Car car;

    @Override
    public void execute() {
        System.out.println("Executing car.stop()");
        car.stop();
    }

    @Override
    public void undo() {
        System.out.println("Executing car.start()");
        car.move();
    }
}
