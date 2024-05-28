package com.ecw.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

	Logger logger = LoggerFactory.getLogger(CloudGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
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
