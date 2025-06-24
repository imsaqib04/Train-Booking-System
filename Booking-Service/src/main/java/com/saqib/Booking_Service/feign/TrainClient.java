package com.saqib.Booking_Service.feign;

//import com.saqib.Booking_Service.dto.Train;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@FeignClient(name = "TRAIN-SERVICE")
//public interface TrainClient {
//
//    @GetMapping("/api/train/getAllTrains")
//    public ResponseEntity<List<Train>> getAllTrains();
//
//    @GetMapping("/api/train/{id}")
//    public ResponseEntity<Train> getTrainById(@PathVariable Long id);
//
//    @PostMapping("/api/train/addTrain")
//    public ResponseEntity<Train> addTrain(@RequestBody Train train);
//
//
//    @PostMapping("/api/train/addTrains")
//    public ResponseEntity<List<Train>> addTrains(@RequestBody List<Train> trains);
//
//
//    @DeleteMapping("/api/train/deleteTrain/{id}")
//    public ResponseEntity<String> deleteTrain(@PathVariable Long id);
//
//
//    @PutMapping("/api/train/updateTrain/{id}")
//    public ResponseEntity<?> updateTrain(@PathVariable Long id, @RequestBody Train trainDetails);
//
//
//    @GetMapping("/api/train/searchBySourceAndDestination")
//    public ResponseEntity<?> searchBySourceAndDestination(@RequestParam String source,
//                                                          @RequestParam String destination);
//
//
//    @GetMapping("/api/train/searchByTrainName")
//    public ResponseEntity<?> getTrainsByName(@RequestParam String trainName);
//
//
//    @GetMapping("/api/train/{trainId}/seats")
//    public int getAvailableSeats(@PathVariable Long trainId,
//                                 @RequestParam("travelDate") String travelDateStr);
//
//}


import com.saqib.Booking_Service.dto.TrainDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "train-service", url = "http://localhost:8093")
public interface TrainClient {

    @GetMapping("/api/train/{id}/total-seats")
    TrainDto getTotalSeats(@PathVariable("id") Long trainId);

    @PutMapping("/api/train/{id}/update-seats")
    void updateAvailableSeats(@PathVariable("id") Long trainId, @RequestParam("seatsToReduce") int seatsToReduce);

    @PutMapping("/api/train/{trainId}/seats/increase")
    void increaseAvailableSeats(@PathVariable Long trainId, @RequestParam int seats);

    @GetMapping("/api/train/{id}")
    TrainDto getTrainById(@PathVariable("id") Long trainId);

}
