package com.saqib.Booking_Service.repo;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByPnrNumber(String pnr);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByJourneyDate(LocalDate journeyDate);

    @Query("SELECT b FROM Booking b WHERE b.trainId = :trainId AND b.journeyDate = :date AND b.status = 'WAITING' ORDER BY b.waitingListPosition ASC")
    List<Booking> findWaitingList(@Param("trainId") Long trainId, @Param("date") LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.status = 'CONFIRMED' AND b.journeyDate = :tomorrow")
    List<Booking> findUpcomingJourneys(@Param("today") LocalDate today, @Param("tomorrow") LocalDate tomorrow);

    List<Booking> findByUserIdAndTrainIdAndJourneyDate(Long userId, Long trainId, LocalDate journeyDate);
}
