package com.saqib.Booking_Service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saqib.Booking_Service.enums.Gender;
import com.saqib.Booking_Service.enums.SeatPreference;
import jakarta.persistence.*;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender; // MALE, FEMALE, OTHER

    @Column(name = "seat_no", length = 10)       // ✅ add this
    private String seatNo;

    private Double fare;

    private Boolean isChild;
    private Boolean isSeniorCitizen;

    private String idProofType; // Aadhaar, Passport, PAN, etc.

    private SeatPreference seatPreference; // Window, Aisle, Middle

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;

    // Getters, Setters, Constructors

    public Passenger() {
    }

    public Passenger(Long passengerId, String name, Integer age, Gender gender, String seatNo, Double fare, Boolean isChild, Boolean isSeniorCitizen, String idProofType, SeatPreference seatPreference, Booking booking) {
        this.passengerId = passengerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNo = seatNo;
        this.fare = fare;
        this.isChild = isChild;
        this.isSeniorCitizen = isSeniorCitizen;
        this.idProofType = idProofType;
        this.seatPreference = seatPreference;
        this.booking = booking;
    }

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

    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Boolean getChild() {
        return isChild;
    }

    public void setChild(Boolean child) {
        isChild = child;
    }

    public Boolean getSeniorCitizen() {
        return isSeniorCitizen;
    }

    public void setSeniorCitizen(Boolean seniorCitizen) {
        isSeniorCitizen = seniorCitizen;
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

