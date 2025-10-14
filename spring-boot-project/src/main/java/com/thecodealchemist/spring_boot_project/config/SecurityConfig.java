// package com.thecodealchemist.spring_boot_project.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;


// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) 
//             .cors(cors -> {})   
//             .authorizeHttpRequests(auth -> auth

//                 .requestMatchers("/api/login", "/register", "/profile", "/profile/update", "/market-items", "/market-items/{id}", "/market-items/category/{category}","/transport/fetchdestination", "/transport/book", "/transport/daily-commute", "/transactions/request", "/transactions/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//               .formLogin(form -> form.disable()) 
//             .httpBasic(httpBasic -> httpBasic.disable()) 
//             .logout(logout -> logout.disable());

//         return http.build();
//     }
// }

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
            .csrf(csrf -> csrf.disable()) // allow POST/PUT/DELETE from frontend
            .cors(cors -> {})             // enable CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/login", "/register", "/user/profile", "/market-items/**",
                    "/transactions/**", "/transport/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .logout(logout -> logout.disable());

        return http.build();
    }
}
