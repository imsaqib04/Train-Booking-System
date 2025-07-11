package com.saqib.Booking_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainDto {

    /* ─── Basic Identification ─── */
    private Long   id;
    private Long   trainId;
    private String trainName;
    private String trainType;          // e.g., SHATABDI, EXPRESS

    /* ─── Route & Schedule ─── */
    private String source;
    private String destination;
    private LocalDate travelDate;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private String   runningDays;      // MON,TUE,…

    /* ─── Capacity / Seats ─── */
    private int passengerCapacity;
    private int availableSeats;

    /** class‑wise available seats */


    @JsonProperty("class2S")
    private int seats2S;

    @JsonProperty("classSl")
    private int seatsSl;

    @JsonProperty("class3Ac")
    private int seats3Ac;

    @JsonProperty("class2Ac")
    private int seats2Ac;

    @JsonProperty("class1Ac")
    private int seats1Ac;

    /* ─── Fare (per seat) ─── */
    private double fare2S;
    private double fareSl;
    private double fare3Ac;
    private double fare2Ac;
    private double fare1Ac;

    /* ─── Extra ─── */
    private String  status;           // ACTIVE / INACTIVE
    private Integer distanceInKm;

    /* Constructors */
    public TrainDto() { }

    /* --- all‑args constructor (optional) omitted for brevity --- */

    /* Getters & Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTrainId() { return trainId; }
    public void setTrainId(Long trainId) { this.trainId = trainId; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getTrainType() { return trainType; }
    public void setTrainType(String trainType) { this.trainType = trainType; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }

    public LocalTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }

    public String getRunningDays() { return runningDays; }
    public void setRunningDays(String runningDays) { this.runningDays = runningDays; }

    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int passengerCapacity) { this.passengerCapacity = passengerCapacity; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public int getSeats2S() { return seats2S; }
    public void setSeats2S(int seats2S) { this.seats2S = seats2S; }

    public int getSeatsSl() { return seatsSl; }
    public void setSeatsSl(int seatsSl) { this.seatsSl = seatsSl; }

    public int getSeats3Ac() { return seats3Ac; }
    public void setSeats3Ac(int seats3Ac) { this.seats3Ac = seats3Ac; }

    public int getSeats2Ac() { return seats2Ac; }
    public void setSeats2Ac(int seats2Ac) { this.seats2Ac = seats2Ac; }

    public int getSeats1Ac() { return seats1Ac; }
    public void setSeats1Ac(int seats1Ac) { this.seats1Ac = seats1Ac; }

    public double getFare2S() { return fare2S; }
    public void setFare2S(double fare2S) { this.fare2S = fare2S; }

    public double getFareSl() { return fareSl; }
    public void setFareSl(double fareSl) { this.fareSl = fareSl; }

    public double getFare3Ac() { return fare3Ac; }
    public void setFare3Ac(double fare3Ac) { this.fare3Ac = fare3Ac; }

    public double getFare2Ac() { return fare2Ac; }
    public void setFare2Ac(double fare2Ac) { this.fare2Ac = fare2Ac; }

    public double getFare1Ac() { return fare1Ac; }
    public void setFare1Ac(double fare1Ac) { this.fare1Ac = fare1Ac; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getDistanceInKm() { return distanceInKm; }
    public void setDistanceInKm(Integer distanceInKm) { this.distanceInKm = distanceInKm; }
}
