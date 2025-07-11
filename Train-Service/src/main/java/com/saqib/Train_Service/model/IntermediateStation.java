package com.saqib.Train_Service.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "intermediate_stations")
public class IntermediateStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    private int stopNumber;  // order of stop
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "train_id")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "train_id",
        referencedColumnName = "train_id",   // ⬅  points to natural key
        nullable = false)

    private Train train;

    public IntermediateStation() {}

    public IntermediateStation(String stationName, String arrivalTime, String departureTime, int stopNumber, Train train) {
        this.stationName = stationName;
        this.stopNumber = stopNumber;
        this.train = train;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
