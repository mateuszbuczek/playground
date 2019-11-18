package com.example.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.file.upload-dir")
@Getter
@Setter
public class FileStorageProperties {

    private String uploadDir;
}
