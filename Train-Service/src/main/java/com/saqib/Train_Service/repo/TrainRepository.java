package com.saqib.Train_Service.repo;

import com.saqib.Train_Service.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train,Long> {

    List<Train> findBySourceAndDestination(String source, String destination);

    List<Train> findByTrainNameIgnoreCase(String trainName);
}
