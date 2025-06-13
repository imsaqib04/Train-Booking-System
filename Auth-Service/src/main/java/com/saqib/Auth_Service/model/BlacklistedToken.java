package com.saqib.Auth_Service.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 512)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    public BlacklistedToken() {
    }

    public BlacklistedToken(String token, Date expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public BlacklistedToken(String token) {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }
}
