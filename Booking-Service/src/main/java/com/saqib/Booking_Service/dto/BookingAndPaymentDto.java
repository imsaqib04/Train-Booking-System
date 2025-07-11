package com.saqib.Booking_Service.dto;

public record BookingAndPaymentDto(
        BookingResponseDto booking,
        PaymentResponseDto payment){
    @Override
    public BookingResponseDto booking() {
        return booking;
    }

    @Override
    public PaymentResponseDto payment() {
        return payment;
    }

    public BookingAndPaymentDto {
    }
}

