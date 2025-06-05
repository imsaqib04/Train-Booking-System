package com.saqib.Booking_Service.dto;

import java.time.LocalDateTime;


public class BookingRequest {
    private Long userId;
    private Long trainId;
    private int SeatsBooked;
    private String passengerName;
    private String travelDate;
    private LocalDateTime bookingDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public int getSeatsBooked() {
        return SeatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        SeatsBooked = seatsBooked;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
}
