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
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                .requestMatchers("/api/login", "/register", "/profile", "/profile/update").permitAll()
=======
                .requestMatchers("/api/login", "/register", "/profile", "/profile/update", "/market-items", "/market-items/{id}", "market-items/category/{category}").permitAll()
>>>>>>> a804cf10fe8195aa9f8926960c35ac84a6269200
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable()) // disable default form login
            .httpBasic(httpBasic -> httpBasic.disable()) // disable basic auth
            .logout(logout -> logout.disable()); // disable Spring default logout filter

        return http.build();
    }
}