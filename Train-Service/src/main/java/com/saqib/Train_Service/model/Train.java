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


     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(unique = true, nullable = false)
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


     private int class2S;          // 1 for true available
     private int classSl;          // 0 for false Unavailable
     private int class3Ac;
     private int class2Ac;
     private int class1Ac;

     private String runningDays;

     public Train() {
     }

     public Train(Long id, Long trainId, String trainName, int passengerCapacity, String trainType, String source, String destination, LocalDate travelDate, LocalTime arrivalTime, LocalTime departureTime, int availableSeats, int class2S, int classSl, int class3Ac, int class2Ac, int class1Ac, String runningDays, String status, double fare2S, double fareSl, double fare3Ac, double fare2Ac, double fare1Ac) {
         this.id = id;
         this.trainId = trainId;
         this.trainName = trainName;
         this.passengerCapacity = passengerCapacity;
         this.source = source;
         this.destination = destination;
         this.travelDate = travelDate;
         this.arrivalTime = arrivalTime;
         this.departureTime = departureTime;
         this.availableSeats = availableSeats;
         this.class2S = class2S;
         this.classSl = classSl;
         this.class3Ac = class3Ac;
         this.class2Ac = class2Ac;
         this.class1Ac = class1Ac;
         this.runningDays = runningDays;
     }

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
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

