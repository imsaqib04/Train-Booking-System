package com.saqib.Booking_Service.repo;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByTrainId(Long trainId);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByTrainIdAndStatus(Long trainId, BookingStatus status);

    @Query("SELECT SUM(b.numberOfSeats) FROM Booking b WHERE b.trainId = :trainId AND b.status = 'CONFIRMED'")
    Integer getTotalConfirmedSeatsByTrainId(@Param("trainId") Long trainId);

    @Query("SELECT COALESCE(SUM(b.numberOfSeats), 0) FROM Booking b WHERE b.trainId = :trainId AND b.status = 'CONFIRMED'")
    int countConfirmedSeatsByTrainId(@Param("trainId") Long trainId);

    int countByTrainId(Long trainId);


    @Query("SELECT COALESCE(SUM(b.numberOfSeats), 0) FROM Booking b WHERE b.trainId = :trainId AND b.status = 'CONFIRMED'")
    int getTotalBookedSeatsByTrainId(@Param("trainId") Long trainId);
}
