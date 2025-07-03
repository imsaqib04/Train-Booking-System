package com.saqib.Train_Service.repo;

import com.saqib.Train_Service.model.IntermediateStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IntermediateStationRepository extends JpaRepository<IntermediateStation, Long> {

    List<IntermediateStation> findByTrainTrainIdOrderByStopNumberAsc(Long trainId);

    /* --- यह नया helper --- */
    @Query("select coalesce(max(s.stopNumber), 0) " +
            "from IntermediateStation s " +
            "where s.train.trainId = :tid")
    Optional<Integer> maxStopNumberByTrain(@Param("tid") Long trainId);
}

