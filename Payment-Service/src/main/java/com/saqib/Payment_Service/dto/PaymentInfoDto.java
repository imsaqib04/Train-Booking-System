package com.saqib.Payment_Service.dto;

public class PaymentInfoDto {
    private String paymentId;

    public PaymentInfoDto() {
    }

    public PaymentInfoDto(String paymentId) {
        this.paymentId = paymentId;  // ✅ This line is **required**
    }
 
    // + getters / setters

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}

