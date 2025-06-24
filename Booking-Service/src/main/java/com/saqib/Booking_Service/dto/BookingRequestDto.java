package com.saqib.Booking_Service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class BookingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long trainId;

    @NotNull
    private String coachType;

    @NotNull
    private LocalDate journeyDate;

    @NotEmpty
    @Size(max = 6, message = "You can only book up to 6 passengers")
    @Valid
    private List<PassengerDto> passengers;

    private String bookingSource; // Optional
    private String bookingMode;

    // Getters, Setters
}
