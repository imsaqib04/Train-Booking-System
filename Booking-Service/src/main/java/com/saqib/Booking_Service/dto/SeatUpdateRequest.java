package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.enums.CoachClass;   // same enum names
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SeatUpdateRequest {

    @NotNull
    private CoachClass coachClass;

    @Positive
    private int seats;

    public SeatUpdateRequest(CoachClass coachType, int paxCount) {
    }

    public SeatUpdateRequest() {

    }

    /* getters & setters */

    public CoachClass getCoachClass() {
        return coachClass;
    }

    public void setCoachClass(CoachClass coachClass) {
        this.coachClass = coachClass;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
