package com.example.demo;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner action(CustomerRepository repository) {
        return (args) -> {
            repository.saveAll(Arrays.asList(
                    new Customer("a", "b"),
                    new Customer("c", "d"),
                    new Customer("e", "f"),
                    new Customer("g", "h")
            )).blockLast(Duration.ofSeconds(10));

            log.info("Customers found");

            repository.findAll().doOnNext(customer -> {
                log.info(customer.toString());
            }).blockLast(Duration.ofSeconds(10));

            repository.findById(1L).doOnNext(customer -> {
                log.info("Customer found with findById(1L):");
                log.info("--------------------------------");
                log.info(customer.toString());
                log.info("");
            }).block(Duration.ofSeconds(10));

            log.info("Customer found with findByLastName('d'):");
            log.info("--------------------------------------------");
            repository.findByLastName("d").doOnNext(d -> {
                log.info(d.toString());
            }).blockLast(Duration.ofSeconds(10));;
            log.info("");
        };

    }
}
