package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.enums.PaymentStatus;

public class PaymentConfirmationDto {
        private Long   bookingId;
        private String paymentId;   // razorPay / stripe ‑ id
        private PaymentStatus status;      // SUCCESS / FAILED / PENDING

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
