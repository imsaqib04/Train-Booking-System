package com.saqib.Train_Service.controller;

import com.saqib.Train_Service.Service.TrainService;

import com.saqib.Train_Service.feign.BookingServiceClient;
import com.saqib.Train_Service.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private BookingServiceClient bookingServiceClient;

    // Get all trains
    @GetMapping("/getAllTrains")
    public ResponseEntity<List<Train>> getAllTrains() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }

    // Get train by ID
    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable Long id) {
        return trainService.getTrainById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add single train
    @PostMapping("/addTrain")
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        return new ResponseEntity<>(trainService.addTrain(train), HttpStatus.CREATED);
    }

    // Add multiple trains
    @PostMapping("/addTrains")
    public ResponseEntity<List<Train>> addTrains(@RequestBody List<Train> trains) {
        return new ResponseEntity<>(trainService.addAllTrains(trains), HttpStatus.CREATED);
    }

    // Delete train
    @DeleteMapping("/deleteTrain/{id}")
    public ResponseEntity<String> deleteTrain(@PathVariable Long id) {
        boolean deleted = trainService.deleteTrain(id);
        if (deleted) {
            return ResponseEntity.ok("Train deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found with ID: " + id);
        }
    }

    // Update train
    @PutMapping("/updateTrain/{id}")
    public ResponseEntity<?> updateTrain(@PathVariable Long id, @RequestBody Train trainDetails) {
        Train updatedTrain = trainService.updateTrain(id, trainDetails);
        if (updatedTrain != null) {
            return ResponseEntity.ok(updatedTrain);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found with ID: " + id);
        }
    }

    // Search by source and destination
    @GetMapping("/searchBySourceAndDestination")
    public ResponseEntity<?> searchBySourceAndDestination(@RequestParam String source,
                                                          @RequestParam String destination) {
        List<Train> trains = trainService.findBySourceAndDestination(source, destination);
        if (trains.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No trains available for the route " + source + " to " + destination);
        }
        return ResponseEntity.ok(trains);
    }

    // Search by train name
    @GetMapping("/searchByTrainName")
    public ResponseEntity<?> getTrainsByName(@RequestParam String trainName) {
        List<Train> trains = trainService.findByTrainName ( trainName );
        if (trains.isEmpty ()) {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND )
                    .body ( "No train found with name: " + trainName );
        }
        return ResponseEntity.ok ( trains );
    }


    @GetMapping("/{trainId}/seats")
    public ResponseEntity<Integer> getAvailableSeats(@PathVariable Long trainId) {
        // Get the train from the TrainService (total seats)
        Optional<Train> optionalTrain = trainService.getTrainById(trainId);

        if (!optionalTrain.isPresent()) {
            return ResponseEntity.notFound().build(); // Train not found
        }

        Train train = optionalTrain.get(); // Extract the Train object
        int totalSeats = train.getTotalSeats();

        // Get the number of booked seats using the BookingServiceClient
        int bookedSeats = bookingServiceClient.getBookedSeatsByTrainId(trainId);

        // Calculate available seats
        int availableSeats = totalSeats - bookedSeats;

        // Return the available seats
        return ResponseEntity.ok(availableSeats);
    }
}


