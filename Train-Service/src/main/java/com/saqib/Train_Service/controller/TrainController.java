package com.saqib.Train_Service.controller;

import com.saqib.Train_Service.Service.TrainService;
import com.saqib.Train_Service.dto.AvailableSeatsDTO;
import com.saqib.Train_Service.dto.SeatUpdateRequest;
import com.saqib.Train_Service.dto.TrainRequestDTO;
import com.saqib.Train_Service.dto.TrainResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    // ────────────────────────────── CRUD ──────────────────────────────

    /** Create one train */
    @PostMapping("/addTrain")
    public ResponseEntity<TrainResponseDTO> create(@Valid @RequestBody TrainRequestDTO dto) {
        return ResponseEntity.ok(trainService.create(dto));
    }

    /** Bulk create */
    @PostMapping("/batch")
    public ResponseEntity<List<TrainResponseDTO>> createBatch(
            @Valid @RequestBody List<TrainRequestDTO> dtoList) {
        return ResponseEntity.ok(trainService.createBatch(dtoList));
    }

    /** Get all (paginated optional) */
    @GetMapping("/getAll")
    public ResponseEntity<List<TrainResponseDTO>> getAll() {
        return ResponseEntity.ok(trainService.getAll());
    }

    /** Get single by trainId */
    @GetMapping("/{trainId}")
    public ResponseEntity<TrainResponseDTO> getOne(@PathVariable Long trainId) {
        return ResponseEntity.ok(trainService.getOne(trainId));
    }

    /** Update */
    @PutMapping("/{trainId}")
    public ResponseEntity<TrainResponseDTO> update(
            @PathVariable Long trainId,
            @Valid @RequestBody TrainRequestDTO dto) {
        return ResponseEntity.ok(trainService.update(trainId, dto));
    }

    /** Delete */
    @DeleteMapping("/{trainId}")
    public ResponseEntity<Void> delete(@PathVariable Long trainId) {
        trainService.delete(trainId);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────── Business‑specific Endpoints ───────────────────────

    /** Seats left / capacity */
    @GetMapping("/{trainId}/total-seats")
    public ResponseEntity<Integer> totalSeats(@PathVariable Long trainId) {
        return ResponseEntity.ok(trainService.getTotalSeats(trainId));
    }
//
//    /** Reduce seats atomically (booking) */
//    @PatchMapping("/{trainId}/seats/reduce")
//    public ResponseEntity<Void> reduceSeats(
//            @PathVariable Long trainId,
//            @RequestParam int seats) {
//        trainService.reduceSeats(trainId, seats);
//        return ResponseEntity.ok().build();
//    }

//    /** Increase seats (cancel) */
//    @PatchMapping("/{trainId}/seats/increase")
//    public ResponseEntity<Void> increaseSeats(
//            @PathVariable Long trainId,
//            @RequestParam int seats) {
//        trainService.increaseSeats(trainId, seats);
//        return ResponseEntity.ok().build();
//    }

    /** Search by name */
    @GetMapping("/by-name")
    public ResponseEntity<List<TrainResponseDTO>> byName(@RequestParam String name) {
        return ResponseEntity.ok(trainService.findByName(name));
    }

    /** Search by source & destination */
    @GetMapping("/by-route")
    public ResponseEntity<List<TrainResponseDTO>> byRoute(
            @RequestParam String source,
            @RequestParam String destination) {
        return ResponseEntity.ok(trainService.findByRoute(source, destination));
    }


    /** Get live available‑seat snapshot */
    @GetMapping("/{trainId}/available-seats")
    public ResponseEntity<AvailableSeatsDTO> available(@PathVariable Long trainId) {
        return ResponseEntity.ok(trainService.getAvailableSeats(trainId));
    }

    /* reduce seats */
    @PatchMapping("/{trainId}/seats/reduce")
    public ResponseEntity<Void> reduce(
            @PathVariable Long trainId,
            @Valid @RequestBody SeatUpdateRequest req) {
        trainService.reduceSeats(trainId, req);
        return ResponseEntity.ok().build();
    }

    /* increase seats */
    @PatchMapping("/{trainId}/seats/increase")
    public ResponseEntity<Void> increase(
            @PathVariable Long trainId,
            @Valid @RequestBody SeatUpdateRequest req) {
        trainService.increaseSeats(trainId, req);
        return ResponseEntity.ok().build();
    }


}
