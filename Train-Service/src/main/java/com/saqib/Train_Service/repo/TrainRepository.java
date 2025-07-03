package com.saqib.Train_Service.repo;

import com.saqib.Train_Service.model.Train;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train,Long> {

    List<Train> findBySourceAndDestination(String source, String destination);

    List<Train> findByTrainNameIgnoreCase(String trainName);

    Optional<Train> findById(Long id);

    Optional<Train> findByTrainId(Long trainId);

    boolean existsByTrainId(Long trainId);

    void deleteByTrainId(Long trainId);


    // ───────────── Pessimistic lock for seat update ─────────────
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Train t where t.trainId = :trainId")
    Optional<Train> findByTrainIdForUpdate(@Param("trainId") Long trainId);
}


