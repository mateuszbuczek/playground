package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
public class HttpSessionConfiguration extends AbstractHttpSessionApplicationInitializer {

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }
}
