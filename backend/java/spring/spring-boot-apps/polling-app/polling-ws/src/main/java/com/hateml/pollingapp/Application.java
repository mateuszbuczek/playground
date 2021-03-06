package com.hateml.pollingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//We’ll be using Java 8 Data/Time classes in our domain models.We’ll need to register JPA2.1converters so that all the Java 8 Date/Time fields in the domain models automatically get converted to SQL types when we persist them in the database.

@SpringBootApplication
@EntityScan(basePackageClasses = {
        Application.class,
        Jsr310JpaConverters.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
