package com.example.multithreadedfileparser.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class Processor {

    private final Executor executingPool;
    private final Store store;

    void startProcessing(String filename) {
        File rootDir = new File(filename);

        executingPool.execute(() -> scan(rootDir));
    }

    void scan(File file) {
        if (file.isDirectory()) {
            for (File fileInDir : file.listFiles()) {
                executingPool.execute(() -> scan(fileInDir));
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " FOUND " + file.getAbsolutePath());
            store.FILES.add(new com.example.multithreadedfileparser.processor.File(file.getAbsolutePath(), file.getName()));
        }
    }

//    Runnable processFile() {
//
//    }
//
//
//    Runnable processDirectory() {
//
//    }
}
