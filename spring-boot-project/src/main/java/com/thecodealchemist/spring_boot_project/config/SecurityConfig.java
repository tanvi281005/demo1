package com.thecodealchemist.spring_boot_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .cors(cors -> {})             // Enable CORS
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/api/login", "/register", "/profile", "/profile/update", "/market-items", "/market-items/{id}", "market-items/category/{category}","/transport/fetchdestination", "/transport/book", "/transport/daily-commute","/urgent","/resources/upload").permitAll()
                .anyRequest().authenticated()
            )
              .formLogin(form -> form.disable()) // disable default form login
            .httpBasic(httpBasic -> httpBasic.disable()) // disable basic auth
            .logout(logout -> logout.disable());

        return http.build();
    }

}
