package com.ecw.gateway.jwt;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    public JwtAuthenticationFilter(JwtReactiveAuthenticationManager authManager,
                                   JwtUtil jwtUtil) {
        super(authManager);

        // Convert login requests to Authentication objects
        this.setServerAuthenticationConverter(new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                return exchange.getRequest().getBody()
                        .next()
                        .flatMap(dataBuffer -> {
                            String body = dataBuffer.toString(java.nio.charset.StandardCharsets.UTF_8);
                            // simple parsing - in real apps use ObjectMapper for JSON
                            String username = body.contains("username") ? body.split("username=")[1].split("&")[0] : null;
                            String password = body.contains("password") ? body.split("password=")[1] : null;
                            return Mono.just(new UsernamePasswordAuthenticationToken(username, password));
                        });
            }
        });

        // Success handler - generate JWT
        this.setAuthenticationSuccessHandler((WebFilterExchange webFilterExchange, Authentication authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String token = jwtUtil.generateToken(authentication.getName(), role);

            webFilterExchange.getExchange().getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            byte[] bytes = ("{\"token\":\"" + token + "\"}").getBytes();
            return webFilterExchange.getExchange().getResponse()
                    .writeWith(Mono.just(webFilterExchange.getExchange().getResponse()
                            .bufferFactory().wrap(bytes)));
        });

        // Failure handler
        this.setAuthenticationFailureHandler((WebFilterExchange webFilterExchange, AuthenticationException exception) -> {
            webFilterExchange.getExchange().getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return webFilterExchange.getExchange().getResponse().setComplete();
        });
    }
}
