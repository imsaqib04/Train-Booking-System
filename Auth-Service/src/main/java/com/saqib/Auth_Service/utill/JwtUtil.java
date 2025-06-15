package com.saqib.Auth_Service.utill;

import com.saqib.Auth_Service.repo.BlacklistedTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    // ✅ Use a 32-character minimum strong secret (or load from properties)
    private static final String SECRET = "YXBpLWdwdC1qc29uLXNlY3VyZS1zZWNyZXQta2V5LWZvci1qc29uLXRva2Vu";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateEmailVerificationToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

//    public boolean isTokenValid(String token) {
//        if (blacklistedTokenRepository != null && blacklistedTokenRepository.findByToken(token).isPresent()) {
//            return false;
//        }
//
//        try {
//            Claims claims = extractAllClaims(token);
//            return !claims.getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String validateTokenAndGetUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);

            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Token expired");
            }

            if (blacklistedTokenRepository != null &&
                    blacklistedTokenRepository.findByToken(token).isPresent()) {
                throw new RuntimeException("Token is blacklisted");
            }

            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }

    public boolean isTokenValid(String token) {
        if (blacklistedTokenRepository.findByToken(token).isPresent()) {
            return false;
        }

        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }



}
