package com.github.deetree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Mariusz Bal
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
                .csrf().disable()
                .securityMatcher("/error/**");
        return http.build();
    }

    @Bean
    PasswordEncoder assignEncoder() {
        return new BCryptPasswordEncoder();
    }
}
