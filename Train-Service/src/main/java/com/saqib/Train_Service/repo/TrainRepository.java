package com.saqib.Train_Service.repo;

import com.saqib.Train_Service.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
