package com.capge.casestudy.apigateway.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Component;
@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Check if Authorization header is present
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    throw new RuntimeException("Invalid Authorization header format");
                }

                try {
                    // Validate token
                    jwtUtil.validateToken(authHeader);

                    // Extract user info
                    String username = jwtUtil.extractUsername(authHeader);
                    String role = jwtUtil.extractRole(authHeader);
                    log.info("Extracted role: " + role);

                    // Role-based path check
                    if (exchange.getRequest().getURI().getPath().contains("/admin") && !role.toLowerCase().equals("ADMIN".toLowerCase())) {
                        throw new RuntimeException("Access Denied! Only ADMINs allowed.");
                    }

                    // Forward user details to downstream service
                    exchange = exchange.mutate()
                            .request(builder -> builder
                                    .header("X-Username", username)
                                    .header("X-Role", role)
                            )
                            .build();

                } catch (Exception e) {
                    log.info("Invalid access: {}" , e.getMessage());
                    throw new RuntimeException("Unauthorized access to application");
                }
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Empty class just to satisfy the generic requirement
    }
}
