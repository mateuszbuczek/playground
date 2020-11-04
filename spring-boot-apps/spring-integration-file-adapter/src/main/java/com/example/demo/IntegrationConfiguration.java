package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

    private static final String FILE_INPUT_CHANNEL = "fileInputChannel";

    @Bean
    @InboundChannelAdapter(value = FILE_INPUT_CHANNEL, poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
        filter.addFilter(new SimplePatternFileListFilter("*.go"));

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("C:\\workspace\\tmp"));
        source.setFilter(filter);
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = FILE_INPUT_CHANNEL)
    public FileWritingMessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("C:\\workspace\\tmp_copy"));
        handler.setAutoCreateDirectory(true);
        handler.setExpectReply(false);
        return handler;
    }
}
