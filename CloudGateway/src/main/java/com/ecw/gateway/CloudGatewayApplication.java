package com.ecw.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/*import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;*/

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

    Logger logger = LoggerFactory.getLogger(CloudGatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

 /*   @Bean
    public RouterFunction<ServerResponse> helloRoute() {
        return route(GET("/hello"), request -> ok().body("Hello from functional endpoint!"));
    }*/

  /*  @Bean
    public RouterFunction<ServerResponse gatewayRouterFunctionsCircuitBreakerFallback() {

        return route("test-route")
                .route(path("/api-test/**"), http("http://localhost:8081"))
                .filter(circuitBreaker(config -> config.setId("myCircuitBreaker")
                        .setFallbackUri("forward:/fallback")
                        .setStatusCodes("500", "NOT_FOUND")))
                .build();
    }*/

 /*   @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> getDefaultCircuitBreaker() {
        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(
                        CircuitBreakerConfig.ofDefaults()
                ).build()
        );
    }*/

/*	@Bean
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
