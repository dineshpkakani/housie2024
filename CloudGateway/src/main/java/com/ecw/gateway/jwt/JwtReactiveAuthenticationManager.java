package com.ecw.gateway.jwt;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public JwtReactiveAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Hardcoded users
        if ("admin".equals(username) && "pass".equals(password)) {
            return Mono.just(new UsernamePasswordAuthenticationToken(username, password,
                    java.util.List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        } else if ("player".equals(username) && "pass".equals(password)) {
            return Mono.just(new UsernamePasswordAuthenticationToken(username, password,
                    java.util.List.of(new SimpleGrantedAuthority("ROLE_PLAYER"))));
        }
        return Mono.error(new BadCredentialsException("Invalid Credentials"));
    }
}
