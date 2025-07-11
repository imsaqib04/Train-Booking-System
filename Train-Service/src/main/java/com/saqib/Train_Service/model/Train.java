 package com.saqib.Train_Service.model;

import com.saqib.Train_Service.enums.TrainStatus;
import com.saqib.Train_Service.enums.TrainType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

 @Entity
 @Table(name = "trains")
 public class Train {

     // Train.java
     @Column(name = "distance_in_km")
     private Integer distanceInKm;

     public Integer getDistanceInKm() { return distanceInKm; }
     public void setDistanceInKm(Integer distanceInKm) { this.distanceInKm = distanceInKm; }


//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

     @Id
     @Column(name = "train_id", unique = true, nullable = false)
     private Long trainId;
     private String trainName;

     @Positive
     private int passengerCapacity;

     @Enumerated(EnumType.STRING)
     private TrainType trainType;

     @Enumerated(EnumType.STRING)
     private TrainStatus status;

     @PositiveOrZero
     private int availableSeats;

     @Column(precision = 10, scale = 2)
     private BigDecimal fare2S;

     @Column(precision = 10, scale = 2)
     private BigDecimal fareSl;

     @Column(precision = 10, scale = 2)
     private BigDecimal fare3Ac;

     @Column(precision = 10, scale = 2)
     private BigDecimal fare2Ac;

     @Column(precision = 10, scale = 2)
     private BigDecimal fare1Ac;

     private String source;
     private String destination;

     private LocalDate travelDate;
     private LocalTime arrivalTime;
     private LocalTime departureTime;



//     private int class2S;          // 1 for true available
//     private int classSl;          // 0 for false Unavailable
//     private int class3Ac;
//     private int class2Ac;
//     private int class1Ac;

     @Column(name = "seats_2s")   // optional: map to DB column
     private int seats2S;

     @Column(name = "seats_sl")
     private int seatsSl;

     @Column(name = "seats_3ac")
     private int seats3Ac;

     @Column(name = "seats_2ac")
     private int seats2Ac;

     @Column(name = "seats_1ac")
     private int seats1Ac;


     private String runningDays;

     public Train() {
     }



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


     public int getSeats2S() {
         return seats2S;
     }

     public void setSeats2S(int seats2S) {
         this.seats2S = seats2S;
     }

     public int getSeatsSl() {
         return seatsSl;
     }

     public void setSeatsSl(int seatsSl) {
         this.seatsSl = seatsSl;
     }

     public int getSeats3Ac() {
         return seats3Ac;
     }

     public void setSeats3Ac(int seats3Ac) {
         this.seats3Ac = seats3Ac;
     }

     public int getSeats2Ac() {
         return seats2Ac;
     }

     public void setSeats2Ac(int seats2Ac) {
         this.seats2Ac = seats2Ac;
     }

     public int getSeats1Ac() {
         return seats1Ac;
     }

     public void setSeats1Ac(int seats1Ac) {
         this.seats1Ac = seats1Ac;
     }

     public String getRunningDays() {
         return runningDays;
     }

     public String getTrainType() {
         return String.valueOf ( trainType );
     }

     public String getStatus() {
         return String.valueOf ( status );
     }

     public void setTrainType(TrainType trainType) {
         this.trainType = trainType;
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

     public void setRunningDays(String runningDays) {
         this.runningDays = runningDays;
     }


     // after intermediate station
     @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<IntermediateStation> intermediateStations = new ArrayList<> ();

     // getter and setter
     public List<IntermediateStation> getIntermediateStations() {
         return intermediateStations;
     }

     public void setIntermediateStations(List<IntermediateStation> intermediateStations) {
         this.intermediateStations = intermediateStations;
     }


 }

