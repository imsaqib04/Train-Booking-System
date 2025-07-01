package com.saqib.Train_Service.controller;

import com.saqib.Train_Service.Service.IntermediateStationService;
import com.saqib.Train_Service.model.IntermediateStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class IntermediateStationController {

    @Autowired
    private IntermediateStationService stationService;

    @PostMapping("/add/{trainId}")
    public ResponseEntity<IntermediateStation> addStation(@PathVariable Long trainId, @RequestBody IntermediateStation station) {
        return ResponseEntity.ok(stationService.addStation(trainId, station));
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<IntermediateStation>> getStationsByTrain(@PathVariable Long trainId) {
        return ResponseEntity.ok(stationService.getStationsByTrainId(trainId));
    }

    @DeleteMapping("/delete/{stationId}")
    public ResponseEntity<String> deleteStation(@PathVariable Long stationId) {
        stationService.deleteStation(stationId);
        return ResponseEntity.ok("Deleted successfully.");
    }
}
