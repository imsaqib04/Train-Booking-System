package com.saqib.Booking_Service.model;

import com.saqib.Booking_Service.enums.CoachClass;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_no")
    private String seatNo;      // e.g. "S1-34"

    @Enumerated(EnumType.STRING)
    private CoachClass coachClass;

    @Column(name = "train_id")
    private Long trainId;

    @Column(name = "journey_date")
    private LocalDate journeyDate;

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public CoachClass getCoachClass() {
        return coachClass;
    }

    public void setCoachClass(CoachClass coachClass) {
        this.coachClass = coachClass;
    }
}
