package com.example.multithreadedfileparser.processor;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Store {
    public final Queue<File> FILES = new ConcurrentLinkedQueue<>();
}
