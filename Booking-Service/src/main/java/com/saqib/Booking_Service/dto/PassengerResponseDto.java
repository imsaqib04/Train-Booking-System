package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.model.Gender;
import com.saqib.Booking_Service.model.SeatPreference;
import lombok.Data;

@Data
public class PassengerResponseDto {

    private Long passengerId;
    private String name;
    private int age;
    private Gender gender;
    private String seatNumber; // optional
    private double fare;
    private String idProofType;
    private SeatPreference seatPreference;
    private Boolean child;
    private Boolean seniorCitizen;

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

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

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
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

    public Boolean getChild() {
        return child;
    }

    public void setChild(Boolean child) {
        this.child = child;
    }

    public Boolean getSeniorCitizen() {
        return seniorCitizen;
    }

    public void setSeniorCitizen(Boolean seniorCitizen) {
        this.seniorCitizen = seniorCitizen;
    }

    public static PassengerResponseDto from(com.saqib.Booking_Service.model.Passenger p) {
        PassengerResponseDto dto = new PassengerResponseDto();
        dto.setPassengerId(p.getPassengerId());
        dto.setName(p.getName());
        dto.setAge(p.getAge());
        dto.setGender(p.getGender());
        dto.setSeatNumber(p.getSeatNumber());
        dto.setFare(p.getFare());
        dto.setIdProofType(p.getIdProofType());
        dto.setSeatPreference(p.getSeatPreference());
        dto.setChild(p.getChild());
        dto.setSeniorCitizen(p.getSeniorCitizen());
        return dto;
    }
}

