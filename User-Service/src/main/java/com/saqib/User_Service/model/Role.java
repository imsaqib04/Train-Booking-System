package com.saqib.User_Service.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    @JsonCreator
    public static Role fromString(String value) {
        switch (value.toLowerCase()) {
            case "user": return ROLE_USER;
            case "admin": return ROLE_ADMIN;
            default: throw new IllegalArgumentException("Invalid role: " + value);
        }
    }
}
