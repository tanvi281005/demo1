package com.thecodealchemist.spring_boot_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .cors(cors -> {})             // Enable CORS
            .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                .requestMatchers("/api/login", "/register", "/profile", "/transport/fetchdestination", "/transport/book", "/transport/daily-commute").permitAll()
=======

                .requestMatchers("/api/login", "/register", "/profile", "/profile/update", "/market-items", "/market-items/{id}", "market-items/category/{category}").permitAll()
 
>>>>>>> a9912d0846a69e3d8f5c19eb90697e7e345c3551
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginProcessingUrl("/api/login") // our login endpoint
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .sessionManagement(session -> session
                .maximumSessions(1) // optional: limit 1 session per user
            );

        return http.build();
    }
<<<<<<< HEAD

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowedOrigins(List.of("http://localhost:5173")); // React
    //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    //     config.setAllowCredentials(true); // important for sessions

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);
    //     return source;
    //}
}
=======
}
>>>>>>> a9912d0846a69e3d8f5c19eb90697e7e345c3551
