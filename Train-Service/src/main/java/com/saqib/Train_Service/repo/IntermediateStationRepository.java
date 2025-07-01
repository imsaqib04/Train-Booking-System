package com.saqib.Train_Service.repo;

import com.saqib.Train_Service.model.IntermediateStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntermediateStationRepository extends JpaRepository<IntermediateStation, Long> {
    List<IntermediateStation> findByTrainTrainIdOrderByStopNumberAsc(Long trainId);
}

