package com.saqib.Train_Service.controller;

import com.saqib.Train_Service.Service.IntermediateStationService;
import com.saqib.Train_Service.dto.IntermediateStationDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class IntermediateStationController {

    private final IntermediateStationService svc;

    public IntermediateStationController(IntermediateStationService svc) {
        this.svc = svc;
    }

    /* ───── Add one ───── */
    @PostMapping("/add/{trainId}")
    public ResponseEntity<IntermediateStationDTO> add(
            @PathVariable Long trainId,
            @Valid @RequestBody IntermediateStationDTO dto) {

        return ResponseEntity.ok(svc.add(trainId, dto));
    }

    /* ───── Batch add ───── */
    @PostMapping("/add/{trainId}/batch")
    public ResponseEntity<List<IntermediateStationDTO>> addBatch(
            @PathVariable Long trainId,
            @Valid @RequestBody List<IntermediateStationDTO> dtos) {

        return ResponseEntity.ok(svc.addBatch(trainId, dtos));
    }

    /* ───── Get list ───── */
    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<IntermediateStationDTO>> list(@PathVariable Long trainId) {
        return ResponseEntity.ok(svc.getByTrain(trainId));
    }

    /* ───── Delete one ───── */
    @DeleteMapping("/delete/{stationId}")     // stationId = id
    public ResponseEntity<Void> delete(@PathVariable Long stationId) {
        svc.delete(stationId);
        return ResponseEntity.noContent().build();
    }
}
