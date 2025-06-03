package com.saqib.Train_Service.controller;

import com.saqib.Train_Service.Service.TrainService;
import com.saqib.Train_Service.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

//    public TrainController(TrainService trainService) {
//        this.trainService = trainService;
//    }

    // for get all trains
    @GetMapping("/getAll")
    public ResponseEntity<List<Train>> getAllTrains() {
        return ResponseEntity.ok ( trainService.getAllTrains () );
    }

    // get Train by Train-id
    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrain(@PathVariable Long id) {
        return trainService.getTrainById ( id )
                .map ( ResponseEntity::ok )
                .orElse ( ResponseEntity.notFound ().build () );
    }

    // adding a train
    @PostMapping("/add")
    public ResponseEntity<Train> createTrain(@RequestBody Train train) {
        return ResponseEntity.ok ( trainService.createTrain ( train ) );
    }

    // Add multiple trains
    @PostMapping("/addTrains")
    public ResponseEntity<List<Train>> addTrains(@RequestBody List<Train> trains) {
        return new ResponseEntity<> ( trainService.addAllTrains ( trains ), HttpStatus.CREATED );
    }

    // get total-seat in a train
    @GetMapping("/{id}/total-seats")
    public ResponseEntity<Integer> getTotalSeats(@PathVariable Long id) {
        return trainService.getTotalSeats ( id )
                .map ( ResponseEntity::ok )
                .orElse ( ResponseEntity.notFound ().build () );
    }

    // get seats reduce matlab seats according to booking km hoti jayengi
    @PutMapping("/{id}/update-seats")
    public ResponseEntity<Void> updateAvailableSeats(
            @PathVariable Long id,
            @RequestParam int seatsToReduce) {

        try {
            trainService.updateAvailableSeats ( id, seatsToReduce );
            return ResponseEntity.ok ().build ();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest ().body ( null );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound ().build ();
        }
    }

    // when ticket cancel then seat increase automatically. according to its seat confirmed or not !
    @PutMapping("/{id}/seats/increase")
    public void increaseSeats(@PathVariable Long id, @RequestParam int seats) {
        trainService.increaseSeats(id, seats);  // Delegate to service
    }

//     Search by train name
    @GetMapping("/searchByTrainName")
    public ResponseEntity<?> getTrainsByName(@RequestParam String trainName) {
        List<Train> trains = trainService.findByTrainName ( trainName );
        if (trains.isEmpty ()) {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND )
                    .body ( "No train found with name: " + trainName );
        }
        return ResponseEntity.ok ( trains );
    }

    // Delete train by id
    @DeleteMapping("/deleteTrain/{id}")
    public ResponseEntity<String> deleteTrain(@PathVariable Long id) {
        boolean deleted = trainService.deleteTrain ( id );
        if (deleted) {
            return ResponseEntity.ok ( "Train deleted successfully." );
        } else {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body ( "Train not found with ID: " + id );
        }
    }

//     Update train
    @PutMapping("/updateTrain/{id}")
    public ResponseEntity<?> updateTrain(@PathVariable Long id, @RequestBody Train trainDetails) {
        Train updatedTrain = trainService.updateTrain ( id, trainDetails );
        if (updatedTrain != null) {
            return ResponseEntity.ok ( updatedTrain );
        } else {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body ( "Train not found with ID: " + id );
        }
    }

    // Search by source and destination
    @GetMapping("/searchBySourceAndDestination")
    public ResponseEntity<?> searchBySourceAndDestination(@RequestParam String source,
                                                          @RequestParam String destination) {
        List<Train> trains = trainService.findBySourceAndDestination ( source, destination );
        if (trains.isEmpty ()) {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND )
                    .body ( "No trains available for the route " + source + " to " + destination );
        }
        return ResponseEntity.ok ( trains );
    }

}
