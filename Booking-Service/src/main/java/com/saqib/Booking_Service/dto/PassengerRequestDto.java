package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.model.Gender;
import com.saqib.Booking_Service.model.SeatPreference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PassengerRequestDto {

    @NotBlank
    private String name;

    @Min(0)
    private int age;

    @NotNull
    private Gender gender;

    private String idProofType;

    private SeatPreference seatPreference;

    private Boolean child;
    private Boolean seniorCitizen;

    public boolean isChild() {
        return child;
    }
    public void setChild(boolean child) {
        this.child = child;
    }

    public boolean isSeniorCitizen() {
        return seniorCitizen;
    }
    public void setSeniorCitizen(boolean seniorCitizen) {
        this.seniorCitizen = seniorCitizen;
    }

    // Getters, Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getIdProofType() {
        return idProofType;
    }

    public void setIdProofType(String idProofType) {
        this.idProofType = idProofType;
    }

    public SeatPreference getSeatPreference() {
        return seatPreference;
    }

    public void setSeatPreference(SeatPreference seatPreference) {
        this.seatPreference = seatPreference;
    }

    }