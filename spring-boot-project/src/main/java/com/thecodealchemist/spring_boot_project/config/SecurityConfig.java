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
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // allow POST/PUT/DELETE from frontend
            .cors(cors -> {})             // enable CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/login", "/register", "/profile", "/profile/update", "/market-items", "/market-items/{id}", "/market-items/category/{category}","/market-items/search","/transport/fetchdestination", "/transport/book", "/transport/daily-commute","/urgent","/resources/upload", "/resources/fetch-subjects","/resources/fetch","/resources/download/**","/resources/notify-me","/resources/notifications/fetch", "/transactions/buyer","/transactions/seller","/user/buysellprofile", "/transactions/request", "/transactions/**", "/counsellor/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .logout(logout -> logout.disable());

        return http.build();
    }

@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // frontend origin
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


//     @Bean
// public CorsConfigurationSource corsConfigurationSource() {
//     CorsConfiguration configuration = new CorsConfiguration();
//     configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//     configuration.setAllowedHeaders(List.of("*"));
//     configuration.setAllowCredentials(true);

//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     source.registerCorsConfiguration("/**", configuration);
//     return source;
// }

}