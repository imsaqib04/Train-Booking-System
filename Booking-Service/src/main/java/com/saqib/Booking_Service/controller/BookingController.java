package com.saqib.Booking_Service.controller;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.dto.BookingRequestDto;
import com.saqib.Booking_Service.dto.BookingResponseDto;
import com.saqib.Booking_Service.exceptions.ResourceNotFoundException;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.service.BookingService;
import com.saqib.Booking_Service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketService ticketService;

//    @Autowired
    private ResourceNotFoundException resourceNotFoundException;


    // 1.book ticket
    @PostMapping("/BookTicket")
    public Object createBooking(@RequestBody BookingRequestDto bookingRequestDto) throws IOException {
        BookingResponseDto createdBooking = bookingService.bookTicket ( bookingRequestDto );

        if (createdBooking.getStatus () == BookingStatus.WAITING) {
            return ResponseEntity.status ( 202 ).body ( createdBooking ); // Accepted but not confirmed
        } else {
            return ResponseEntity.ok ( createdBooking ); // Confirmed
        }
    }

    // 2.cancel booking by id
    @PutMapping("/cancel/bookingId/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam String reason) {
        bookingService.cancelBooking ( bookingId, reason );
        return ResponseEntity.ok ( "✅ Booking cancelled and waiting list handled" );
    }

    // 3.download ticket invoice
    @GetMapping("/downloadTicket/{bookingId}")
    public Object downloadTicket(@PathVariable Long bookingId) throws IOException {
        ByteArrayInputStream pdf = ticketService.generateTicketPdf ( bookingId );

        HttpHeaders headers = new HttpHeaders ();
        headers.setContentType ( MediaType.APPLICATION_PDF );
        headers.setContentDispositionFormData ( "attachment", "Ticket-" + bookingId + ".pdf" );

        return new ResponseEntity<> ( pdf, headers, HttpStatus.OK );
    }

    // 4. Cancel booking by PNR
    @PutMapping("/cancel/pnr/{pnr}")
    public ResponseEntity<String> cancelBooking(@PathVariable String pnr, @RequestParam String reason) {
        return ResponseEntity.ok ( bookingService.cancelBooking ( pnr, reason ) );
    }

    // 5. Get booking by PNR
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<BookingResponseDto> getBookingByPnr(@PathVariable String pnr) {
        return ResponseEntity.ok ( bookingService.getBookingByPnr ( pnr ) );
    }

    // 6.Get all bookings for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok ( bookingService.getBookingsByUser ( userId ) );
    }

    //  7.Update payment status
    @PutMapping("/payment-status/{pnr}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable String pnr,
                                                      @RequestParam String status) {
        return ResponseEntity.ok ( bookingService.updatePaymentStatus ( pnr, status ) );
    }

    //  8.Get all bookings (admin use only)
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok ( bookingService.getAllBookings () );
    }

    // 9. Get bookings by journey date (format: yyyy-MM-dd)
    @GetMapping("/journey-date")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByJourneyDate(
            @RequestParam String date) {
        return ResponseEntity.ok ( bookingService.getBookingsByJourneyDate ( date ) );
    }

    // 10. Get booking status by PNR
    @GetMapping("/status/{pnr}")
    public ResponseEntity<String> getBookingStatus(@PathVariable String pnr) {
        return ResponseEntity.ok(bookingService.getBookingStatus(pnr));
    }

    // 11. Get booking details by booking ID (admin/internal)
    @GetMapping("/id/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
    }

}

