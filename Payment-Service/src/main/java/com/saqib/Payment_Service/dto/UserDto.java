package com.saqib.Payment_Service.dto;

import java.util.List;

public class UserDto {
    private Long   userId;
    private String fullName;
    private String email;

    private List<String> roles;   //  ← new

    // getters / setters …
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public Long getUserId()      { return userId; }
    public void setUserId(Long v){ userId = v; }

    public String getFullName()  { return fullName; }
    public void setFullName(String v){ fullName = v; }

    public String getEmail()     { return email; }
    public void setEmail(String v){ email = v; }

}

