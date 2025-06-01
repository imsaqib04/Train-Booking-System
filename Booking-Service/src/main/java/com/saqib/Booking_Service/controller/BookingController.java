package com.saqib.Booking_Service.controller;

import com.saqib.Booking_Service.dto.BookingRequest;
import com.saqib.Booking_Service.dto.BookingStatusResponse;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookTicket(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.bookTicket(request));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // BookingController.java (inside Booking Service)

    @GetMapping("/{trainId}/bookedSeats")
    public ResponseEntity<Integer> getBookedSeatsByTrainId(@PathVariable Long trainId) {
        int bookedSeats = bookingService.getBookedSeatsByTrainId(trainId); // implement this method in your service
        return ResponseEntity.ok(bookedSeats);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/checkBooking")
    public ResponseEntity<BookingStatusResponse> checkBookingStatus(@RequestBody BookingRequest bookingRequest) {
        // Call service to process the booking
        Booking booking = bookingService.processBooking(bookingRequest);

        // Return the status (CONFIRMED or WAITING)
        return ResponseEntity.ok(new BookingStatusResponse(booking.getStatus()));
    }
}
//http://booking-service/bookings/1/bookedSeats