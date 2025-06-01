package com.saqib.Booking_Service.feign;

import com.saqib.Booking_Service.dto.Train;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "TRAIN-SERVICE", url = "http://localhost:8081/api")
public interface TrainClient {

    @GetMapping("/train/{trainId}")
    Train getTrainById(@PathVariable Long trainId);

    @GetMapping("/train/{trainId}/seats")
    int getAvailableSeats(@PathVariable Long trainId);

    @PutMapping("/train/{trainId}/seats")
    void updateAvailableSeats(@PathVariable("trainId") Long trainId, @RequestParam("availableSeats") int availableSeats);

}
