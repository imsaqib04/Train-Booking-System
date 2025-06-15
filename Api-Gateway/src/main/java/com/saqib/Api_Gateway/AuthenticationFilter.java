//package com.saqib.Api_Gateway;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//
//            if (!request.getHeaders().containsKey( HttpHeaders.AUTHORIZATION)) {
//                throw new RuntimeException("Missing Authorization Header");
//            }
//
//            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                String token = authHeader.substring(7);
//                try {
//                    jwtUtil.validateToken(token); // your custom JWT validator
//                } catch (Exception e) {
//                    throw new RuntimeException("Invalid Token");
//                }
//            }
//
//            return chain.filter(exchange);
//        };
//    }
//
//    public static class Config {
//    }
//}
