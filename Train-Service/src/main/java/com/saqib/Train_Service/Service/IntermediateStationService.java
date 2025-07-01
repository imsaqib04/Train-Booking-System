package com.saqib.Train_Service.Service;

import com.saqib.Train_Service.model.IntermediateStation;
import com.saqib.Train_Service.model.Train;
import com.saqib.Train_Service.repo.IntermediateStationRepository;
import com.saqib.Train_Service.repo.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntermediateStationService {

    @Autowired
    private IntermediateStationRepository stationRepository;

    @Autowired
    private TrainRepository trainRepository;

    public List<IntermediateStation> getStationsByTrainId(Long trainId) {
        return stationRepository.findByTrainTrainIdOrderByStopNumberAsc(trainId);
    }

    public IntermediateStation addStation(Long trainId, IntermediateStation station) {
        Train train = trainRepository.findByTrainId(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        station.setTrain(train);
        return stationRepository.save(station);
    }

    public void deleteStation(Long stationId) {
        stationRepository.deleteById(stationId);
    }
}
