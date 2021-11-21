package com.example.multithreadedfileparser;

import com.example.multithreadedfileparser.processor.File;
import com.example.multithreadedfileparser.processor.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {

    private final Store store;

    @GetMapping
    public List<File> get() {
        return new ArrayList<>(store.FILES);
    }
}
