package com.example.demo.structural.adapter;

public class NewLineFormatter implements TextFormattable {

    @Override
    public String formatText(String text) {
        return text.replace(".", "\n");
    }
}
