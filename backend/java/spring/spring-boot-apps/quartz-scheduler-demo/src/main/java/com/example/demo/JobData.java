package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class JobData {
    private String jobName;
    private String jobGroup;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime startTime;
    private int counter;
    private int gapDuration;
}
