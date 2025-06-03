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

//    public TrainService(TrainRepository trainRepository) {
//        this.trainRepository = trainRepository;
//    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Optional<Train> getTrainById(Long id) {
        return trainRepository.findById(id);
    }

    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    public Optional<Integer> getTotalSeats(Long id) {
        return trainRepository.findById(id)
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

    public void increaseSeats(Long id, int seats) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));
        train.setAvailableSeats(train.getAvailableSeats() + seats);
        trainRepository.save(train);
    }

    //Search by train name (case-insensitive)
    public List<Train> findByTrainName(String Name) {
        return trainRepository.findByNameIgnoreCase(Name);
    }

    // Add multiple trains
    public List<Train> addAllTrains(List<Train> trains) {
        return trainRepository.saveAll(trains);
    }


    // Delete train by ID with success flag
    public boolean deleteTrain(Long id) {
        if (trainRepository.existsById(id)) {
            trainRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Update train details
    public Train updateTrain(Long id, Train trainDetails) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + id));

        train.setName (trainDetails.getName());
        train.setSource(trainDetails.getSource());
        train.setDestination(trainDetails.getDestination());
        train.setDate ( trainDetails.getDate () );
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

