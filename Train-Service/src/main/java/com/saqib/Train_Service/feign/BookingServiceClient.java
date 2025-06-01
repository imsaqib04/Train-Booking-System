package com.saqib.Train_Service.feign;


import com.saqib.Train_Service.dto.Booking;
import com.saqib.Train_Service.dto.BookingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingServiceClient {

    @GetMapping("/booking/{trainId}/seats")
    int getAvailableSeats(@PathVariable Long trainId);


    @PostMapping("/booking/book")
    ResponseEntity<Booking> bookTicket(@RequestBody BookingRequest request);


    // Method to get the total booked seats by train ID (add @GetMapping to make it work with Feign)
    @GetMapping("/booking/{trainId}/bookedSeats")
    int getBookedSeatsByTrainId(@PathVariable("trainId") Long trainId);
}
