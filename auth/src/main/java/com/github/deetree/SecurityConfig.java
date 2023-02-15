package com.github.deetree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Mariusz Bal
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(request -> request.requestMatchers("/api/**").permitAll());
        return http.build();
    }
}
