package com.ecw.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                    .pathMatchers(
                            "/auth/**",
                            "/web-portal/**",
                            "/favicon.ico",
                            "/css/**",
                            "/js/**",
                            "/images/**",
                            "/webjars/**",
                            "/web-portal/**",     // static resources from web-portal service
                            "/login.html",
                            "/register.html",
                            "/.well-known/**"   // 👈 allow Chrome / well-known checks devtools request internally
                    ).permitAll()
                .anyExchange().authenticated()
            )
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
        return http.build();
    }
}
