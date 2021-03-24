package com.example.demo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class FlywayConfiguration {

    private final Environment environment;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
            .dataSource(
                    environment.getRequiredProperty("spring.flyway.url"),
                    environment.getRequiredProperty("spring.flyway.user"),
                    environment.getRequiredProperty("spring.flyway.password")
            )
        );
    }
}
