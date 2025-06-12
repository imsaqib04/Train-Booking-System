//package com.saqib.Auth_Service.model;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//public class BlacklistedToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, length = 500)
//    private String token;
//
//    private Date expiry;
//
//    public BlacklistedToken() {}
//
//    public BlacklistedToken(String token, Date expiry) {
//        this.token = token;
//        this.expiry = expiry;
//    }
//
//    public BlacklistedToken(String token, LocalDateTime localExpiry) {
//    }
//
//    // Getters and Setters
//    public String getToken() { return token; }
//    public void setToken(String token) { this.token = token; }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Date getExpiry() {
//        return expiry;
//    }
//
//    public void setExpiry(Date expiry) {
//        this.expiry = expiry;
//    }
//}
//
