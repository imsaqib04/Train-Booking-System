package com.saqib.Booking_Service.repo;

import com.saqib.Booking_Service.enums.CoachClass;
import com.saqib.Booking_Service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    @Query("""
  SELECT COALESCE(SUM(b.totalSeats), 0)
  FROM   Booking b
  WHERE  b.trainId        = :trainId
     AND b.journeyDate    = :date
     AND b.coachType      = :coachType
     AND b.fromStopNumber < :toStop
     AND b.toStopNumber   > :fromStop
     AND b.status         = 'CONFIRMED'
""")
    Integer segmentSeats(@Param("trainId")   Long       trainId,
                         @Param("date")      LocalDate   date,
                         @Param("coachType") CoachClass  coachType,
                         @Param("fromStop")  int         fromStop,
                         @Param("toStop")    int         toStop);

    Optional<Booking> findByBookingId(Long bookingId);

}
