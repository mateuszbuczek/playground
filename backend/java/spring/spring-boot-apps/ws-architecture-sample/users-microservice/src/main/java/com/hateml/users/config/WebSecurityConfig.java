package com.hateml.users.config;

import com.hateml.users.api.security.AuthenticationFilter;
import com.hateml.users.boundary.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Users users;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationFilter authFilter = new AuthenticationFilter(users, env, authenticationManager());
        authFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
        .and()
                .addFilter(authFilter);
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(users).passwordEncoder(passwordEncoder);
    }
}
