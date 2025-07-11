package com.saqib.Booking_Service.dto;

import java.time.LocalDate;


public class UserResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String state;
    private Integer pinCode;

    /* Optional – shown on ticket / invoice */
    private String profileImageUrl;

    /* ───────── Getters & Setters ───────── */

    public Long getId()               { return id; }
    public void setId(Long id)        { this.id = id; }

    public String getFullName()       { return fullName; }
    public void setFullName(String n) { this.fullName = n; }

    public String getEmail()          { return email; }
    public void setEmail(String e)    { this.email = e; }

    public String getPhoneNumber()    { return phoneNumber; }
    public void setPhoneNumber(String p){ this.phoneNumber = p; }

    public String getGender()         { return gender; }
    public void setGender(String g)   { this.gender = g; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate d){ this.dateOfBirth = d; }

    public String getAddress()        { return address; }
    public void setAddress(String a)  { this.address = a; }

    public String getCity()           { return city; }
    public void setCity(String c)     { this.city = c; }

    public String getState()          { return state; }
    public void setState(String s)    { this.state = s; }

    public Integer getPinCode()       { return pinCode; }
    public void setPinCode(Integer p) { this.pinCode = p; }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String u){ this.profileImageUrl = u; }
}
