package com.saqib.Booking_Service.repo;

//import com.saqib.Booking_Service.BookingStatus;
//import com.saqib.Booking_Service.model.Booking;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface BookingRepository extends JpaRepository<Booking, Long> {
//
//    List<Booking> findByUserId(Long userId);
//
//    int countByTrainId(Long trainId);
//
//    @Query("SELECT COALESCE(SUM(b.numberOfSeats), 0) FROM Booking b WHERE b.trainId = :trainId AND b.status = 'CONFIRMED'")
//    int getTotalBookedSeatsByTrainId(@Param("trainId") Long trainId);
//
//    List<Booking> findByTrainIdAndTravelDateAndStatus(Long trainId, LocalDate travelDate, BookingStatus status);
//
//}


import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Count only confirmed bookings for train
    int countByTrainIdAndStatus(Long trainId, BookingStatus status);

    // Find max waiting list position for a train
    @Query("SELECT MAX(b.waitingListPosition) FROM Booking b WHERE b.trainId = :trainId AND b.status = 'WAITING'")
    Integer findMaxWaitingListPositionByTrainId(Long trainId);

    List<Booking> findByTrainIdAndStatusOrderByWaitingListPositionAsc(Long trainId, BookingStatus bookingStatus);

    List<Booking> findByTrainIdAndStatusAndWaitingListPositionGreaterThanOrderByWaitingListPositionAsc(Long trainId, BookingStatus bookingStatus, Integer promotedPosition);
}
