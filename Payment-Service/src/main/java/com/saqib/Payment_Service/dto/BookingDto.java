package com.saqib.Payment_Service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingDto {

    /* IDs */
    private Long   bookingId;
    private String pnrNumber;

    /* Links to other services */
    private Long   userId;        // so we can call User‑Service
    private Long trainId;   // so we can call Train‑Service

    /* Journey & payment info */
    private LocalDate  journeyDate;
    private int        seatCount;
    private BigDecimal totalFare;

    /* ---------- getters / setters ---------- */
    public Long getBookingId()      { return bookingId; }
    public void setBookingId(Long v){ this.bookingId = v; }

    public String getPnrNumber()    { return pnrNumber; }
    public void setPnrNumber(String v){ this.pnrNumber = v; }

    public Long getUserId()         { return userId; }
    public void setUserId(Long v)   { this.userId = v; }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public LocalDate getJourneyDate(){ return journeyDate; }
    public void setJourneyDate(LocalDate v){ this.journeyDate = v; }

    public int getSeatCount()       { return seatCount; }
    public void setSeatCount(int v) { this.seatCount = v; }

    public BigDecimal getTotalFare(){ return totalFare; }
    public void setTotalFare(BigDecimal v){ this.totalFare = v; }
}
