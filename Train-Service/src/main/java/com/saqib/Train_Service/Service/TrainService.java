package com.saqib.Train_Service.Service;
import com.saqib.Train_Service.model.Train;
import com.saqib.Train_Service.repo.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Optional<Train> getTrainById(Long trainId) {
        return trainRepository.findByTrainId (trainId);
    }

    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    public Optional<Integer> getTotalSeats(Long trainId) {
        return trainRepository.findByTrainId ( trainId )
                .map(Train::getPassengerCapacity);
    }


    public void updateAvailableSeats(Long trainId, int seatsToReduce) {
        Train train = trainRepository.findByTrainId(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        int updatedSeats = train.getAvailableSeats() - seatsToReduce;
        if (updatedSeats < 0) {
            throw new IllegalArgumentException("Not enough available seats");
        }

        train.setAvailableSeats(updatedSeats);
        trainRepository.save(train);
    }

    public void increaseSeats(Long trainId, int seats) {
        Train train = trainRepository.findByTrainId (trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));
        train.setAvailableSeats(train.getAvailableSeats() + seats);
        trainRepository.save(train);
    }

    //Search by train name (case-insensitive)
    public List<Train> findByTrainName(String trainName) {
        return trainRepository.findByTrainNameIgnoreCase(trainName);
    }

    // Add multiple trains
    public List<Train> addAllTrains(List<Train> trains) {
        return trainRepository.saveAll(trains);
    }


    // Delete train by ID with success flag
    public boolean deleteTrain(Long trainId) {
        if (trainRepository.existsByTrainId(trainId)) {
            trainRepository.deleteByTrainId(trainId);
            return true;
        }
        return false;
    }


    public Train updateTrain(Long trainId, Train updatedTrain) {
        Train train = trainRepository.findByTrainId (trainId)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + trainId));

        train.setTrainId(updatedTrain.getTrainId());
        train.setTrainName(updatedTrain.getTrainName());
        train.setPassengerCapacity(updatedTrain.getPassengerCapacity());
        train.setTrainType(updatedTrain.getTrainType());
        train.setSource(updatedTrain.getSource());
        train.setDestination(updatedTrain.getDestination());
        train.setTravelDate(updatedTrain.getTravelDate());
        train.setArrivalTime(updatedTrain.getArrivalTime());
        train.setDepartureTime(updatedTrain.getDepartureTime());
        train.setAvailableSeats(updatedTrain.getAvailableSeats());
        train.setClassSl(updatedTrain.getClassSl());
        train.setClass3Ac(updatedTrain.getClass3Ac());
        train.setClass2Ac(updatedTrain.getClass2Ac());
        train.setRunningDays(updatedTrain.getRunningDays());
        train.setStatus(updatedTrain.getStatus());
        train.setFareSl(updatedTrain.getFareSl());
        train.setFare3Ac(updatedTrain.getFare3Ac());
        train.setFare2Ac(updatedTrain.getFare2Ac());

        return trainRepository.save(train);
    }

    // Search by source and destination
    public List<Train> findBySourceAndDestination(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination);
    }
}

