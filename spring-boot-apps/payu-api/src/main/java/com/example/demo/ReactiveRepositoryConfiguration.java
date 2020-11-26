package com.example.demo;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@EnableTransactionManagement
@RequiredArgsConstructor
public class ReactiveRepositoryConfiguration extends AbstractR2dbcConfiguration {

    private final Environment environment;

    @Bean
    ConnectionFactory postgresConnectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(environment.getRequiredProperty("db.host"))
                        .database(environment.getRequiredProperty("db.database"))
                        .port(Integer.parseInt(environment.getRequiredProperty("db.port")))
                        .username(environment.getRequiredProperty("db.username"))
                        .password(environment.getRequiredProperty("db.password"))
                        .schema(environment.getRequiredProperty("db.schema"))
                .build());
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return postgresConnectionFactory();
    }
}
