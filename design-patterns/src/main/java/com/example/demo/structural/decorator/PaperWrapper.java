package com.example.demo.structural.decorator;

public class PaperWrapper extends FlowerBouquetDecorator {

    FlowerBouquet flowerBouquet;

    public PaperWrapper(FlowerBouquet flowerBouquet) {
        this.flowerBouquet = flowerBouquet;
    }

    @Override
    public String getDescription() {
        return flowerBouquet.getDescription() + ", paper wrapper";
    }

    @Override
    public double cost() {
        return flowerBouquet.cost() + 3;
    }
}
