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


import com.saqib.Booking_Service.dto.SeatUpdateRequest;
import com.saqib.Booking_Service.dto.TrainDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "train-service", url = "http://localhost:8093")
//public interface TrainClient {
//
//    @GetMapping("/api/train/{id}/total-seats")
//    TrainDto getTotalSeats(@PathVariable("id") Long trainId);
//
//    @PutMapping("/api/train/{id}/update-seats")
//    void updateAvailableSeats(@PathVariable("id") Long trainId, @RequestParam("seatsToReduce") int seatsToReduce);
//
//    @PutMapping("/api/train/{trainId}/seats/increase")
//    void increaseAvailableSeats(@PathVariable Long trainId, @RequestParam int seats);
//
//    @GetMapping("/api/train/{id}")
//    TrainDto getTrainById(@PathVariable("id") Long trainId);
//
//}


@FeignClient(name = "TRAIN-SERVICE", url = "http://localhost:8093")
public interface TrainClient {

    /* ── Basic GET ───────────────────────────────────────────── */
    @GetMapping("/api/trains/{id}")
    TrainDto getTrainById(@PathVariable("id") Long trainId);

    /* ── Class‑wise seat adjust ──────────────────────────────── */
    @PostMapping("/api/trains/{id}/seats/reduce")
    void reduceSeats(@PathVariable("id") Long trainId,
                     @RequestBody SeatUpdateRequest request);

    @PostMapping("/api/trains/{id}/seats/increase")
    void increaseSeats(@PathVariable("id") Long trainId,
                       @RequestBody SeatUpdateRequest request);

    /** TrainClient extra helper */
    @GetMapping("/api/trains/{id}/station/{name}")
    Integer getStopNumber(@PathVariable Long id,
                          @PathVariable String name);



}

