
package com.saqib.Booking_Service.repo;

import com.saqib.Booking_Service.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query(value = """
        SELECT s.seat_no
        FROM seat s
        WHERE s.coach_class = :coach              -- SL / AC3 …
          AND s.train_id     = :trainId
          AND s.journey_date = :journeyDate
          AND NOT EXISTS (
                SELECT 1
                FROM   booking b
                WHERE  b.seat_no        = s.seat_no
                  AND  b.train_id       = :trainId
                  AND  b.journey_date   = :journeyDate
                  AND  b.from_stop_number < :toStop
                  AND  b.to_stop_number   > :fromStop
                  AND  b.status          = 'CONFIRMED'
          )
        LIMIT :count
        """, nativeQuery = true)
    List<String> findFreeSeats(@Param("coach")      String    coach,
                               @Param("trainId")    Long      trainId,
                               @Param("journeyDate")LocalDate journeyDate,
                               @Param("fromStop")   int       fromStop,
                               @Param("toStop")     int       toStop,
                               @Param("count")      int       count);
}
