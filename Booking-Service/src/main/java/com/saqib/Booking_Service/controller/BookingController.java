package com.saqib.Booking_Service.controller;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.dto.BookingRequest;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/BookTicket")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking createdBooking = bookingService.createBooking ( bookingRequest );

        if (createdBooking.getStatus () == BookingStatus.WAITING) {
            return ResponseEntity.status ( 202 ).body ( createdBooking ); // Accepted but not confirmed
        } else {
            return ResponseEntity.ok ( createdBooking ); // Confirmed
        }
    }


    // Get all bookings
    @GetMapping("/getAll")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok ( bookingService.getAllBookings () );
    }

    // Get booking by id
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return bookingService.getBookingById ( id )
                .map ( ResponseEntity::ok )
                .orElse ( ResponseEntity.notFound ().build () );
    }

    // Cancel a booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        String result = String.valueOf ( bookingService.cancelBooking ( id ) );
        if (result.contains ( "failed" )) {
            return ResponseEntity.badRequest ().body ( result );
        }
        return ResponseEntity.ok ( result );
    }
    @GetMapping("/generate")
    public String generatePNR(@RequestParam Long trainId, @RequestParam Long userId) {
        return bookingService.generatePNR(trainId, userId);
    }
}
