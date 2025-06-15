//package com.saqib.Api_Gateway;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtUtil {
//
//    private final String SECRET = "your-secret-key"; // Must match the Auth Service
//
//    public void validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(SECRET.getBytes())
//                    .parseClaimsJws(token);
//        } catch (Exception e) {
//            throw new RuntimeException("JWT validation failed: " + e.getMessage());
//        }
//    }
//
//    public String extractEmail(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET.getBytes())
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}
