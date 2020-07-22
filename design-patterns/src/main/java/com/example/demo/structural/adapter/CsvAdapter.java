package com.example.demo.structural.adapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CsvAdapter implements TextFormattable {

    private final CsvFormattable csvFormattable;

    @Override
    public String formatText(String text) {
        return csvFormattable.formatCsvText(text);
    }
}
