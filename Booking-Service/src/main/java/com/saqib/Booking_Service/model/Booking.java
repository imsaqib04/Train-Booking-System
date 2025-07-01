package com.saqib.Booking_Service.model;

import com.saqib.Booking_Service.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String email;
    private String trainName;
    private String Source;
    private String Destination;

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Long userId;        // From User Service
    private Long trainId;       // From Train Service

    private Integer totalSeats;
    private Double totalFare;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // CONFIRMED, WAITING, CANCELLED

    @Enumerated(EnumType.STRING)
    private CoachType coachType;   // SL, 3AC, 2AC

    private LocalDate journeyDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PENDING, SUCCESS, FAILED

    private String paymentId;   // Optional (for online payments)

    @Enumerated(EnumType.STRING)
    private BookingSource bookingSource; // Web, App, Counter

    @Enumerated(EnumType.STRING)
    private BookingMode bookingMode;   // Online, Tatkal, General

    private String pnrNumber;     // Unique 10-digit identifier

    private Integer waitingListPosition;

    private String cancellationReason;

    private LocalDateTime cancellationDate;


    // OneToMany relationship with passengers
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers;


    private String generateRandomPNR() {
        Random random = new Random();
        return String.format("%010d", random.nextLong() % 1_000_000_0000L);
    }

    @PrePersist
    public void onCreate() {
        this.bookingTime = LocalDateTime.now();
        this.pnrNumber = generateRandomPNR();
    }


    public Booking() {
    }

    public Booking(Long bookingId, Long userId, Long trainId, Integer totalSeats, Double totalFare, LocalDateTime bookingTime, BookingStatus status, String coachType, LocalDate journeyDate, PaymentStatus paymentStatus, String paymentId, String bookingSource, String bookingMode, String pnrNumber, Integer waitingListPosition, String cancellationReason, LocalDateTime cancellationDate, List<Passenger> passengers) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.trainId = trainId;
        this.totalSeats = totalSeats;
        this.totalFare = totalFare;
//        this.bookingTime = bookingTime;
        this.status = status;
        this.journeyDate = journeyDate;
        this.paymentStatus = paymentStatus;
        this.paymentId = paymentId;
//        this.pnrNumber = pnrNumber;
        this.waitingListPosition = waitingListPosition;
        this.cancellationReason = cancellationReason;
        this.cancellationDate = cancellationDate;
        this.passengers = passengers;
    }
// Getters, Setters, Constructors

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }



    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public Integer getWaitingListPosition() {
        return waitingListPosition;
    }

    public void setWaitingListPosition(Integer waitingListPosition) {
        this.waitingListPosition = waitingListPosition;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDateTime getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(LocalDateTime cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(CoachType coachType) {
        this.coachType = coachType;
    }

    public BookingSource getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(BookingSource bookingSource) {
        this.bookingSource = bookingSource;
    }

    public BookingMode getBookingMode() {
        return bookingMode;
    }

    public void setBookingMode(BookingMode bookingMode) {
        this.bookingMode = bookingMode;
    }
}
