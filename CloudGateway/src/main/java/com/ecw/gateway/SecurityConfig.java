package com.ecw.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


    @Configuration
  //  @EnableWebSecurity
    public class SecurityConfig {


    /*    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/hello","/login.html","/login/**", "/css/**", "/js/**","/login/**").permitAll() // Allow access to login page and static resources
                    .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                    .loginPage("/login.html") // Specify your custom login page URL
                    .permitAll() // Allow all users to access the login page
                )
                .logout(logout -> logout
                    .permitAll() // Allow all users to access the logout functionality
                );
            return http.build();
        }*/



        // ... (UserDetailsService and PasswordEncoder configuration if needed)

    }