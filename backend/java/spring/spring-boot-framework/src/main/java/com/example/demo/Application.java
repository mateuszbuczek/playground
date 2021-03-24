package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CustomImportBeanDefinitionRegistrar.class})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .listeners(new ApplicationEventsLogger())
                .run(args);

//        load everything manually
//        new SpringApplicationBuilder(Application.class)
//                .sources(new Class<?>[] {
//                        JettyServletWebServerFactory.class,
//                }).run(args);
    }
}
