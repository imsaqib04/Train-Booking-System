package com.saqib.Train_Service.dto;

import com.saqib.Train_Service.enums.CoachClass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SeatUpdateRequest {

    @NotNull
    private CoachClass coachClass;  // e.g., SLEEPER

    @Positive
    private int seats;              // how many to reduce/increase

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
