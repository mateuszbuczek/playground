package com.example.demo.structural.decorator;

public abstract class FlowerBouquet {

    String description;
    public abstract double cost();

    public String getDescription() {
        return description;
    }
}
