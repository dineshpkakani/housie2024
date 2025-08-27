package com.ecw.gateway.config;

import com.ecw.gateway.jwt.JwtAuthenticationFilter;
import com.ecw.gateway.jwt.JwtReactiveAuthenticationManager;
import com.ecw.gateway.jwt.JwtSecurityContextRepository;
import com.ecw.gateway.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {


    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public JwtReactiveAuthenticationManager authenticationManager() {
        return new JwtReactiveAuthenticationManager(jwtUtil);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/auth/**",
                                "/auth/player/**",
                                "/web-portal/**",
                                "/favicon.ico",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/login.html",
                                "/register.html",
                                "/.well-known/**"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager())
                .securityContextRepository(new JwtSecurityContextRepository(jwtUtil, authenticationManager()))
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .build();
    }
}
