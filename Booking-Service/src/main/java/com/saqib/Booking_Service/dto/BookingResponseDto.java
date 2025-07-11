package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.enums.*;
import com.saqib.Booking_Service.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingResponseDto {

    // adding these
    private String name;
    private String email;
    private String trainName;
    private String source;
    private String destination;


    private Long bookingId;
    private Long userId;
    private Long trainId;
    private int totalSeats;
    private double totalFare;
    private LocalDateTime bookingTime;
    private BookingStatus status;
    private CoachClass coachType;
    private LocalDate journeyDate;
    private PaymentStatus paymentStatus;
    private String paymentId;
    private BookingSource bookingSource;
    private BookingMode bookingMode;
    private String pnrNumber;
    private Integer waitingListPosition;
    private String cancellationReason;
    private LocalDateTime cancellationDate;
    private List<PassengerResponseDto> passengers;

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

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
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

    public CoachClass getCoachType() {
        return coachType;
    }

    public void setCoachType(CoachClass coachType) {
        this.coachType = coachType;
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

    public List<PassengerResponseDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerResponseDto> passengers) {
        this.passengers = passengers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public static BookingResponseDto from(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        /* ----  ENRICHED FIELDS ---- */
        dto.setName(booking.getName());
        dto.setEmail(booking.getEmail());
        dto.setTrainName(booking.getTrainName());
        dto.setSource(booking.getSource());
        dto.setDestination(booking.getDestination());
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUserId());
        dto.setTrainId(booking.getTrainId());
        dto.setTotalSeats(booking.getTotalSeats());
        dto.setTotalFare(booking.getTotalFare());
        dto.setBookingTime(booking.getBookingTime());
        dto.setStatus(booking.getStatus());
        dto.setCoachType(booking.getCoachType ());
        dto.setJourneyDate(booking.getJourneyDate());
        dto.setPaymentStatus(booking.getPaymentStatus());
        dto.setPaymentId(booking.getPaymentId());
        dto.setBookingSource(booking.getBookingSource());
        dto.setBookingMode(booking.getBookingMode());
        dto.setPnrNumber(booking.getPnrNumber());
        dto.setWaitingListPosition(booking.getWaitingListPosition());
        dto.setCancellationReason(booking.getCancellationReason());
        dto.setCancellationDate(booking.getCancellationDate());

        dto.setPassengers(
                booking.getPassengers().stream()
                        .map(PassengerResponseDto::from)
                        .toList()
        );
        return dto;
    }
}

