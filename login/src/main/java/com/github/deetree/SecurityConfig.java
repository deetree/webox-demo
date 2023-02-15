package com.github.deetree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * @author Mariusz Bal
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private AccountDetailsService detailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(request -> request.requestMatchers("/**", "/api/**").permitAll())
                .formLogin(form -> form.loginPage("/signin"));
        return http.build();
    }

    @Bean
    PasswordEncoder assignEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(detailsService)
                .passwordEncoder(assignEncoder())
                .and()
                .build();
    }
}
