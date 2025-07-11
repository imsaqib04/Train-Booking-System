package com.saqib.Booking_Service.dto;

public class PaymentInfoDto {
    private String paymentId;

    public PaymentInfoDto() { }

    public PaymentInfoDto(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
