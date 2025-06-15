package com.saqib.Auth_Service.dto;

public class LoginResponse {
    private String token;
    private String message;
    private String nextStepUrl;   // later add on

    // Constructors
    public LoginResponse() {}

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
        this.nextStepUrl = "http://localhost:8092/api/user/profile/create";
    }

    // Getters & Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNextStepUrl() {
        return nextStepUrl;
    }

    public void setNextStepUrl(String nextStepUrl) {
        this.nextStepUrl = nextStepUrl;
    }
}

