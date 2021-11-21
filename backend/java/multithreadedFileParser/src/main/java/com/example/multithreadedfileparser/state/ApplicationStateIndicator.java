package com.example.multithreadedfileparser.state;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Objects;

@Endpoint
@RequiredArgsConstructor
class ApplicationStateIndicator implements HealthIndicator {

    private final ApplicationStateStore stateStore;

    @Override
    public Health health() {
        if (Objects.equals(stateStore.getApplicationState(), ApplicationState.FAILED)) {
            return Health.down().withDetail("status", stateStore.getApplicationState()).build();
        } else {
            return Health.up().withDetail("status", stateStore.getApplicationState()).build();
        }
    }
}
