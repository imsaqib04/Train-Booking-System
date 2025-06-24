package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.model.BookingMode;
import com.saqib.Booking_Service.model.BookingSource;
import com.saqib.Booking_Service.model.CoachType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class BookingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long trainId;

    @NotNull
    private CoachType coachType;

    @NotNull
    private LocalDate journeyDate;

    @NotEmpty
    @Size(max = 6, message = "You can only book up to 6 passengers")
    @Valid
    private List<PassengerRequestDto> passengers;

    private BookingSource bookingSource; // Optional
    private BookingMode bookingMode;

    // Getters, Setters

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


    public CoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(CoachType coachType) {
        this.coachType = coachType;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public List<PassengerRequestDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRequestDto> passengers) {
        this.passengers = passengers;
    }

    public BookingSource getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(BookingSource bookingSource) {
        this.bookingSource = bookingSource;
    }

    public BookingMode getBookingMode() {
        return bookingMode;
    }

    public void setBookingMode(BookingMode bookingMode) {
        this.bookingMode = bookingMode;
    }
}
