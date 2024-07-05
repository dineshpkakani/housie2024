package com.ecw.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

	Logger logger = LoggerFactory.getLogger(CloudGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> getDefaultCircuitBreaker() {
		return factory -> factory.configureDefault(
				id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(
						CircuitBreakerConfig.ofDefaults()
				).build()
		);
	}

	/*@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {

			return builder.routes()
				.route("ADMIN-PORTAL", r -> r.path("/event/**")
						//.filters((Function<GatewayFilterSpec, UriSpec>) gatewayFilter)
						.uri("http://localhost:8082"))
				.route("WEB-PORTAL",r->r.path("/login.html")
						.filters(filter -> {
							filter.addResponseHeader("res-header", "res-header-value");
							return filter;
						})
						.uri("http://localhost:8083"))
				.build();
	}*/
}
