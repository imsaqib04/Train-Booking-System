package com.saqib.Booking_Service.dto;

/**
 * Returned by PaymentService#createOrder(..)
 * so the caller (Booking Service, API Gateway, or UI) can
 *   • open Razorpay Checkout (needs key + orderId + amount + currency)
 *   • know which booking it belongs to (either bookingId or pnr)
 */
public class PaymentResponseDto {

    /** Razorpay order reference – required */
    private String orderId;

    /** Your own identifiers – one of these will be non-null */
    private Long   bookingId;   // numeric PK          (optional)
    private String pnr;         // human-readable PNR  (optional)

    /** Display info for Razorpay Checkout */
    private int    amount;      // in whole currency units (e.g., 825)
    private String currency;    // "INR"
    private String razorpayKey; // public key for frontend or caller

    public <T> PaymentResponseDto() {
    }

    /* ---------- getters & setters ---------- */

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getRazorpayKey() { return razorpayKey; }
    public void setRazorpayKey(String razorpayKey) { this.razorpayKey = razorpayKey; }

    /* ---------- helper (optional) ---------- */

    /** True if this response carries a numeric bookingId */
    public boolean hasBookingId() { return bookingId != null; }

    /** True if this response carries a PNR string */
    public boolean hasPnr()       { return pnr != null && !pnr.isBlank(); }
}

