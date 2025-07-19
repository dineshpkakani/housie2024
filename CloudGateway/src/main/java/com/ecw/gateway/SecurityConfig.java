package com.ecw.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
    @EnableWebSecurity
    public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(httpObj->httpObj.anyRequest().permitAll());

        return http.build();
    }


      /*  @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/hello","/login.html","/login**", "/css/**", "/js/**","/login/**").permitAll() // Allow access to login page and static resources
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