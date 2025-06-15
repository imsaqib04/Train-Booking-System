package com.saqib.User_Service.dto;
import java.time.LocalDate;

public class UserProfileRequestDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;

    private String address;
    private String city;
    private String state;
    private Long pinCode;

    private String aadhaarNumber;
    private String panNumber;

    private String profileImageUrl;

    // Constructors
    public UserProfileRequestDto() {
    }

    public UserProfileRequestDto(String name, String email, String phoneNumber, String gender, LocalDate dateOfBirth,
                                 String address, String city, String state, Long pinCode,
                                 String aadhaarNumber, String panNumber, String profileImageUrl) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.aadhaarNumber = aadhaarNumber;
        this.panNumber = panNumber;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public Long getPinCode() {
        return pinCode;
    }
    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
