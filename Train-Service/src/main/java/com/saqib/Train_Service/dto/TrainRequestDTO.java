package com.saqib.Train_Service.dto;

import com.saqib.Train_Service.enums.TrainStatus;
import com.saqib.Train_Service.enums.TrainType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainRequestDTO {

    @NotNull
    private Long trainId;

    @NotBlank
    private String trainName;

    @Positive
    private int passengerCapacity;

    @NotNull
    private TrainType trainType;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @NotNull
    private LocalDate travelDate;

    @NotNull
    private LocalTime arrivalTime;

    @NotNull
    private LocalTime departureTime;

    @PositiveOrZero
    private int availableSeats;

    private int class2S;
    private int classSl;
    private int class3Ac;
    private int class2Ac;
    private int class1Ac;

    private String runningDays;

    @NotNull
    private TrainStatus status;

    @DecimalMin("0.00")
    private BigDecimal fare2S;
    private BigDecimal fareSl;
    private BigDecimal fare3Ac;
    private BigDecimal fare2Ac;
    private BigDecimal fare1Ac;

    private Integer distanceInKm;

    public Integer getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(Integer distanceInKm) {
        this.distanceInKm = distanceInKm;
    }
// --- getters & setters (IDE generate) ---

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getClass2S() {
        return class2S;
    }

    public void setClass2S(int class2S) {
        this.class2S = class2S;
    }

    public int getClassSl() {
        return classSl;
    }

    public void setClassSl(int classSl) {
        this.classSl = classSl;
    }

    public int getClass3Ac() {
        return class3Ac;
    }

    public void setClass3Ac(int class3Ac) {
        this.class3Ac = class3Ac;
    }

    public int getClass2Ac() {
        return class2Ac;
    }

    public void setClass2Ac(int class2Ac) {
        this.class2Ac = class2Ac;
    }

    public int getClass1Ac() {
        return class1Ac;
    }

    public void setClass1Ac(int class1Ac) {
        this.class1Ac = class1Ac;
    }

    public String getRunningDays() {
        return runningDays;
    }

    public void setRunningDays(String runningDays) {
        this.runningDays = runningDays;
    }

    public TrainStatus getStatus() {
        return status;
    }

    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    public BigDecimal getFare2S() {
        return fare2S;
    }

    public void setFare2S(BigDecimal fare2S) {
        this.fare2S = fare2S;
    }

    public BigDecimal getFareSl() {
        return fareSl;
    }

    public void setFareSl(BigDecimal fareSl) {
        this.fareSl = fareSl;
    }

    public BigDecimal getFare3Ac() {
        return fare3Ac;
    }

    public void setFare3Ac(BigDecimal fare3Ac) {
        this.fare3Ac = fare3Ac;
    }

    public BigDecimal getFare2Ac() {
        return fare2Ac;
    }

    public void setFare2Ac(BigDecimal fare2Ac) {
        this.fare2Ac = fare2Ac;
    }

    public BigDecimal getFare1Ac() {
        return fare1Ac;
    }

    public void setFare1Ac(BigDecimal fare1Ac) {
        this.fare1Ac = fare1Ac;
    }
}
