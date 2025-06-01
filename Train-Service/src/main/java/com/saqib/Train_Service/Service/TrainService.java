package com.saqib.Train_Service.Service;
import com.saqib.Train_Service.feign.BookingServiceClient;
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

    @Autowired
    private BookingServiceClient bookingServiceClient;

    // Get all trains
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    // Get train by ID
    public Optional<Train> getTrainById(Long id) {
        return trainRepository.findById(id);
    }


    public Train addTrain(Train train) {
        if (train.getTravelDate() == null) {
            throw new IllegalArgumentException("Travel date must not be null.");
        }

        if (train.getTravelDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Travel date cannot be in the past.");
        }

        return trainRepository.save(train);
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

        train.setTrainName(trainDetails.getTrainName());
        train.setSource(trainDetails.getSource());
        train.setDestination(trainDetails.getDestination());
        train.setTravelDate(trainDetails.getTravelDate());
        train.setDepartureTime(trainDetails.getDepartureTime());
        train.setTotalSeats(trainDetails.getTotalSeats());

        return trainRepository.save(train);
    }

    // Search by source and destination
    public List<Train> findBySourceAndDestination(String source, String destination) {
        return trainRepository.findBySourceAndDestination(source, destination);
    }

    // Search by train name (case-insensitive)
    public List<Train> findByTrainName(String trainName) {
        return trainRepository.findByTrainNameIgnoreCase(trainName);
    }


    public int getAvailableSeats(Long trainId) {
        // Get the train from the database
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + trainId));

        // Get the total seats from the train
        int totalSeats = train.getTotalSeats();

        // Get the booked seats (using the BookingServiceClient to fetch this from the Booking service)
        int bookedSeats = bookingServiceClient.getBookedSeatsByTrainId(trainId);

        // Calculate available seats
        int availableSeats = totalSeats - bookedSeats;

        return availableSeats;
    }


    public void updateAvailableSeats(Long trainId, int newAvailableSeats) {
        Train train = trainRepository.findById(trainId).orElseThrow(() -> new IllegalArgumentException("Train not found"));
        train.setAvailableSeats(newAvailableSeats);
        trainRepository.save(train);
    }

    public Train findById(Long trainId) {
        Optional<Train> trainOpt = trainRepository.findById(trainId);
        return trainOpt.orElse(null); // Return the train if found, otherwise return null
    }

    // Save or update a train
    public void save(Train train) {
        trainRepository.save(train); // Persist the train object to the database
    }
}
