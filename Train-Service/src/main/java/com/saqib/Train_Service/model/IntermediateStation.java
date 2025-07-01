package com.saqib.Train_Service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "intermediate_stations")
public class IntermediateStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    private String arrivalTime;
    private String departureTime;

    private int stopNumber;  // order of stop

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id")
    private Train train;

    public IntermediateStation() {}

    public IntermediateStation(String stationName, String arrivalTime, String departureTime, int stopNumber, Train train) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
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
