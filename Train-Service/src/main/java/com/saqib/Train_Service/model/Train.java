package com.saqib.Train_Service.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String trainName;
    private String source;
    private String destination;
    private LocalDate travelDate;
    private LocalTime departureTime;
    private int availableSeats;
    private int totalSeats; // This keeps track of the total number of seats.

    public Train() {
    }

    public Train(String trainName, String source, String destination,
                 LocalDate travelDate, LocalTime departureTime, int totalSeats) {
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // Initially, available seats should match total seats
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
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

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // Reset available seats when total seats are updated
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // Book seats and adjust available seats
//    public boolean bookSeat(int numberOfSeats) {
//        if (availableSeats >= numberOfSeats) {
//            availableSeats -= numberOfSeats;
//            return true;  // Booking successful
//        } else {
//            return false; // Not enough seats available
//        }
//    }
    public boolean bookSeat(int numberOfSeats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats to book must be positive.");
        }
        if (availableSeats >= numberOfSeats) {
            availableSeats -= numberOfSeats;
            return true;  // Booking successful
        } else {
            return false; // Not enough seats available
        }
    }

    public void setAvailableSeats(int availableSeats) {
        if (availableSeats < 0) {
            throw new IllegalArgumentException("Available seats cannot be negative.");
        }
        this.availableSeats = availableSeats;
    }

}
