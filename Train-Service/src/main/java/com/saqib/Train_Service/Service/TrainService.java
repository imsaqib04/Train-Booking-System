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
        return trainRepository.findById(trainId);
    }

    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    public Optional<Integer> getTotalSeats(Long trainId) {
        return trainRepository.findById(trainId)
                .map(Train::getTotalSeats);
    }


    public void updateAvailableSeats(Long trainId, int seatsToReduce) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        int updatedSeats = train.getAvailableSeats() - seatsToReduce;
        if (updatedSeats < 0) {
            throw new IllegalArgumentException("Not enough available seats");
        }

        train.setAvailableSeats(updatedSeats);
        trainRepository.save(train);
    }

    public void increaseSeats(Long trainId, int seats) {
        Train train = trainRepository.findById(trainId)
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
        if (trainRepository.existsById(trainId)) {
            trainRepository.deleteById(trainId);
            return true;
        }
        return false;
    }

    // Update train details
    public Train updateTrain(Long trainId, Train trainDetails) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + trainId));

        train.setTrainName (trainDetails.getTrainName ());
        train.setSource(trainDetails.getSource());
        train.setDestination(trainDetails.getDestination());
        train.setTravelDate ( trainDetails.getTravelDate () );
        train.setDepartureTime(trainDetails.getDepartureTime());
        train.setArrivalTime (trainDetails.getArrivalTime ());
        train.setTrainClass ( trainDetails.getTrainClass () );
        train.setTotalSeats(trainDetails.getTotalSeats());
        train.setAvailableSeats ( train.getAvailableSeats () );

        return trainRepository.save(train);
    }

    // Search by source and destination
    public List<Train> findBySourceAndDestination(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination);
    }

}

