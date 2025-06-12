package com.saqib.Auth_Service.dto;

public class OAuth2UserProfileDTO {
    private String userName;
    private String email;
    private String provider;

    public OAuth2UserProfileDTO(String userName, String email, String provider) {
        this.userName = userName;
        this.email = email;
        this.provider = provider;
    }

    // Getters & Setters
    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getProvider() {
        return provider;
    }
}
