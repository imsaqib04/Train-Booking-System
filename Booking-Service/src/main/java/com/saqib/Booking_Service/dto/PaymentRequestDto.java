package com.saqib.Booking_Service.dto;

public class PaymentRequestDto {
    /** Either one (or both) must be supplied */
    private Long   bookingId;
    private String pnr;

    private int    amount;
    private String currency = "INR";
    private String receipt;   // optional

    public PaymentRequestDto(Long bookingId, String pnr, String currency) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.currency = currency;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public boolean hasBookingId() { return bookingId != null; }
    public boolean hasPnr()       { return pnr != null && !pnr.isBlank(); }
}
