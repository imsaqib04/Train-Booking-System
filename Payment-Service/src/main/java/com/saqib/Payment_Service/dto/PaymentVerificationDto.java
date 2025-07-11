package com.saqib.Payment_Service.dto;

public class PaymentVerificationDto {
    private Long   bookingId;   // optional
    private String pnr;         // optional

    private String orderId;
    private String paymentId;
    private String signature;
    /* getters / setters */

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /** Return true if a numeric primary-key is present */
    public boolean hasBookingId() {
        return bookingId != null;
    }

    /** Return true if a non-blank PNR string is present */
    public boolean hasPnr() {
        return pnr != null && !pnr.isBlank();
    }
}
