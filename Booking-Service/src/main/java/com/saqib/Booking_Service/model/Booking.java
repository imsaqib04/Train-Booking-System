package com.saqib.Booking_Service.model;


//import com.saqib.Booking_Service.BookingStatus;
//import com.saqib.Booking_Service.dto.Train;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import java.time.LocalDate;

//@Entity
//public class Booking {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long bookingId;
//
//    private Long userId;
//    private Long trainId;
//
//    @Min(1)
//    @Max(6)
//    private int numberOfSeats;
//
//    private LocalDate bookingDate;
//
//    private LocalDate travelDate;
//
//    @Enumerated(EnumType.STRING)
//    private BookingStatus status;
//
//    private double totalFare;
//
//    @Transient
//    private Train train;  // Transient to avoid DB mapping and use Feign/REST call to fetch train details
//
//    // Getters and Setters
//    public Long getBookingId() { return bookingId; }
//    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
//
//    public Long getUserId() { return userId; }
//    public void setUserId(Long userId) { this.userId = userId; }
//
//    public Long getTrainId() { return trainId; }
//    public void setTrainId(Long trainId) { this.trainId = trainId; }
//
//    public int getNumberOfSeats() { return numberOfSeats; }
//    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
//
//    public LocalDate getBookingDate() { return bookingDate; }
//    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
//
//    public BookingStatus getStatus() { return status; }
//    public void setStatus(BookingStatus status) { this.status = status; }
//
//    public double getTotalFare() { return totalFare; }
//    public void setTotalFare(double totalFare) { this.totalFare = totalFare; }
//
//    public Train getTrain() { return train; }
//    public void setTrain(Train train) { this.train = train; }
//
//    public LocalDate getTravelDate() {
//        return travelDate;
//    }
//
//    public void setTravelDate(LocalDate travelDate) {
//        this.travelDate = travelDate;
//    }
//}


import com.saqib.Booking_Service.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Version
    private Integer version;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Long trainId;

    private String passengerName;
    private int seatsBooked;
    private LocalDate travelDate;

    private Double totalFare;

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // e.g. "CONFIRMED", "CANCELLED", "WAITING"

    private LocalDateTime bookingDate;

    private LocalDateTime cancelDate;

    private Integer waitingListPosition; // nullable, if booking is on waiting list

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Integer getWaitingListPosition() {
        return waitingListPosition;
    }

    public void setWaitingListPosition(Integer waitingListPosition) {
        this.waitingListPosition = waitingListPosition;
    }
}

