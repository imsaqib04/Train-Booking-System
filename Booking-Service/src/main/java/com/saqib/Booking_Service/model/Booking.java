package com.saqib.Booking_Service.model;

import com.saqib.Booking_Service.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "booking")   // (optional) explicit table name
public class Booking {

    /* ──────────────────────────────────
     *  Core identifiers
     * ────────────────────────────────── */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;     // User‑Service FK
    private Long trainId;    // Train‑Service FK

    /* ──────────────────────────────────
     *  Enriched data for quick reads
     * ────────────────────────────────── */
    private String name;          // passenger / user name
    private String email;         // contact e‑mail
    private String trainName;
    private String source;
    private String destination;

    /* ──────────────────────────────────
     *  Seats & fare
     * ────────────────────────────────── */
    private Integer totalSeats;
    private Double  totalFare;

    /* ──────────────────────────────────
     *  Metadata
     * ────────────────────────────────── */
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus  status;        // CONFIRMED / WAITING / CANCELLED

    @Enumerated(EnumType.STRING)
    private CoachClass     coachType;     // SITTING / SL / AC3 ...

    private LocalDate journeyDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus  paymentStatus; // PENDING / SUCCESS / FAILED

    private String paymentId;

    @Enumerated(EnumType.STRING)
    private BookingSource bookingSource;  // WEB / APP / COUNTER

    @Enumerated(EnumType.STRING)
    private BookingMode   bookingMode;    // GENERAL / TATKAL

    private String  pnrNumber;            // 10‑digit unique
    private Integer waitingListPosition;

    private String  cancellationReason;
    private LocalDateTime cancellationDate;

    /* ──────────────────────────────────
     *  Relation: passengers
     * ────────────────────────────────── */
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers;

    /* ──────────────────────────────────
     *  Lifecycle hooks
     * ────────────────────────────────── */
    private String generateRandomPNR() {
        Random rnd = new Random();
        return String.format("%010d", Math.abs(rnd.nextLong()) % 1_000_000_0000L);
    }

    @PrePersist
    public void onCreate() {
        this.bookingTime = LocalDateTime.now();
        this.pnrNumber   = generateRandomPNR();

    }


    private Integer fromStopNumber;  // e.g., 3
    private Integer toStopNumber;    // e.g., 7

    @Column(name = "seat_no")
    private String seatNo;

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Integer getFromStopNumber() {
        return fromStopNumber;
    }

    public void setFromStopNumber(Integer fromStopNumber) {
        this.fromStopNumber = fromStopNumber;
    }

    public Integer getToStopNumber() {
        return toStopNumber;
    }

    public void setToStopNumber(Integer toStopNumber) {
        this.toStopNumber = toStopNumber;
    }

    /* ──────────────────────────────────
     *  Constructors
     * ────────────────────────────────── */
    public Booking() { }

    /* ──────────────────────────────────
     *  Getters & Setters
     * ────────────────────────────────── */
    public Long getBookingId()                { return bookingId; }
    public void setBookingId(Long bookingId)  { this.bookingId = bookingId; }

    public Long getUserId()                   { return userId; }
    public void setUserId(Long userId)        { this.userId = userId; }

    public Long getTrainId()                  { return trainId; }
    public void setTrainId(Long trainId)      { this.trainId = trainId; }

    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }

    public String getEmail()                  { return email; }
    public void setEmail(String email)        { this.email = email; }

    public String getTrainName()              { return trainName; }
    public void setTrainName(String trainName){ this.trainName = trainName; }

    public String getSource()                 { return source; }
    public void setSource(String source)      { this.source = source; }

    public String getDestination()            { return destination; }
    public void setDestination(String destination){ this.destination = destination; }

    public Integer getTotalSeats()            { return totalSeats; }
    public void setTotalSeats(Integer seats)  { this.totalSeats = seats; }

    public Double getTotalFare()              { return totalFare; }
    public void setTotalFare(Double fare)     { this.totalFare = fare; }

    public LocalDateTime getBookingTime()     { return bookingTime; }
    public void setBookingTime(LocalDateTime t){ this.bookingTime = t; }

    public BookingStatus getStatus()          { return status; }
    public void setStatus(BookingStatus st)   { this.status = st; }

    public CoachClass getCoachType()          { return coachType; }
    public void setCoachType(CoachClass c)    { this.coachType = c; }

    public LocalDate getJourneyDate()         { return journeyDate; }
    public void setJourneyDate(LocalDate d)   { this.journeyDate = d; }

    public PaymentStatus getPaymentStatus()   { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus p){ this.paymentStatus = p; }

    public String getPaymentId()              { return paymentId; }
    public void setPaymentId(String id)       { this.paymentId = id; }

    public BookingSource getBookingSource()   { return bookingSource; }
    public void setBookingSource(BookingSource s){ this.bookingSource = s; }

    public BookingMode getBookingMode()       { return bookingMode; }
    public void setBookingMode(BookingMode m) { this.bookingMode = m; }

    public String getPnrNumber()              { return pnrNumber; }
    public void setPnrNumber(String pnr)      { this.pnrNumber = pnr; }

    public Integer getWaitingListPosition()   { return waitingListPosition; }
    public void setWaitingListPosition(Integer w){ this.waitingListPosition = w; }

    public String getCancellationReason()     { return cancellationReason; }
    public void setCancellationReason(String r){ this.cancellationReason = r; }

    public LocalDateTime getCancellationDate(){ return cancellationDate; }
    public void setCancellationDate(LocalDateTime d){ this.cancellationDate = d; }

    public List<Passenger> getPassengers()    { return passengers; }
    public void setPassengers(List<Passenger> p){ this.passengers = p; }
}
