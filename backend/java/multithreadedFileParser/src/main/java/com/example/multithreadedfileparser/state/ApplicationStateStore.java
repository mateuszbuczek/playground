package com.example.multithreadedfileparser.state;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationStateStore {
    private ApplicationState applicationState = ApplicationState.IDLE;
}
