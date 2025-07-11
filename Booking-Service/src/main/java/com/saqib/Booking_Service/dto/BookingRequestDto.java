package com.saqib.Booking_Service.dto;

import com.saqib.Booking_Service.enums.BookingMode;
import com.saqib.Booking_Service.enums.BookingSource;
import com.saqib.Booking_Service.enums.CoachClass;
import com.saqib.Booking_Service.enums.CoachType;
import com.saqib.Booking_Service.model.Booking;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class BookingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long trainId;

    @Column(name="coach_type", length = 10)
    @NotNull
    private CoachClass coachType;

    @NotNull
    private LocalDate journeyDate;

    @NotEmpty
    @Size(max = 6, message = "You can only book up to 6 passengers")
    @Valid
    private List<PassengerRequestDto> passengers;

    private BookingSource bookingSource; // Optional
    private BookingMode bookingMode;

    @NotBlank
    private String fromStation;      // new

    @NotBlank
    private String toStation;        // new

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }
// Getters, Setters

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

    public List<PassengerRequestDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRequestDto> passengers) {
        this.passengers = passengers;
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

    public static BookingResponseDto from(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();

        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUserId());
        dto.setTrainId(booking.getTrainId());
        dto.setTotalSeats(booking.getTotalSeats());
        dto.setTotalFare(booking.getTotalFare());
        dto.setBookingTime(booking.getBookingTime());
        dto.setStatus(booking.getStatus());
        dto.setCoachType(booking.getCoachType());
        dto.setJourneyDate(booking.getJourneyDate());
        dto.setPaymentStatus(booking.getPaymentStatus());
        dto.setPaymentId(booking.getPaymentId());
        dto.setBookingSource(booking.getBookingSource());
        dto.setBookingMode(booking.getBookingMode());
        dto.setPnrNumber(booking.getPnrNumber());
        dto.setWaitingListPosition(booking.getWaitingListPosition());
        dto.setCancellationReason(booking.getCancellationReason());
        dto.setCancellationDate(booking.getCancellationDate());

        /* 🔽🔽  यहीं नीचे “enriched” फ़ील्ड copy करें  🔽🔽 */
        dto.setName(booking.getName());                // NEW
        dto.setEmail(booking.getEmail());              // NEW
        dto.setTrainName(booking.getTrainName());      // NEW
        dto.setSource(booking.getSource());            // NEW
        dto.setDestination(booking.getDestination());  // NEW

        /* passengers list */
        dto.setPassengers(
                booking.getPassengers()
                        .stream()
                        .map(PassengerResponseDto::from)
                        .toList()
        );

        return dto;
    }

}
