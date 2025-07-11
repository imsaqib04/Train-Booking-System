package com.saqib.Booking_Service.controller;

import com.saqib.Booking_Service.dto.*;
import com.saqib.Booking_Service.dto.PaymentInfoDto;
import com.saqib.Booking_Service.exceptions.ResourceNotFoundException;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//@RestController
//@RequestMapping("/booking")
//@CrossOrigin(origins = {"http://localhost:5500","http://127.0.0.1:5500"})
//public class BookingController {
//
//    @Autowired
//    private BookingService bookingService;
//
////    @Autowired
////    private TicketService ticketService;
//
////    @Autowired
//    private ResourceNotFoundException resourceNotFoundException;
//
//
//    //1.
//    @PostMapping("/BookTicket")
//    public ResponseEntity<BookingAndPaymentDto> createBooking(
//            @RequestBody BookingRequestDto dto) throws IOException, InterruptedException {
//
//        BookingAndPaymentDto all = bookingService.bookTicket(dto);
//        return ResponseEntity.ok(all);
//    }
//
//
//    // 2.cancel booking by id
//    @PutMapping("/cancel/bookingId/{bookingId}")
//    public ResponseEntity<String> cancelBooking(
//            @PathVariable Long bookingId,
//            @RequestParam String reason) {
//        bookingService.cancelBooking ( bookingId, reason );
//        return ResponseEntity.ok ( "✅ Booking cancelled and waiting list handled" );
//    }
//
////    // 3.download ticket invoice
////    @GetMapping("/downloadTicket/{bookingId}")
////    public Object downloadTicket(@PathVariable Long bookingId) throws IOException {
////        ByteArrayInputStream pdf = ticketService.generateTicketPdf ( bookingId );
////
////        HttpHeaders headers = new HttpHeaders ();
////        headers.setContentType ( MediaType.APPLICATION_PDF );
////        headers.setContentDispositionFormData ( "attachment", "Ticket-" + bookingId + ".pdf" );
////
////        return new ResponseEntity<> ( pdf, headers, HttpStatus.OK );
////    }
//
//    // 4. Cancel booking by PNR
//    @PutMapping("/cancel/pnr/{pnr}")
//    public ResponseEntity<String> cancelBooking(@PathVariable String pnr, @RequestParam String reason) {
//        return ResponseEntity.ok ( bookingService.cancelBooking ( pnr, reason ) );
//    }
//
//    // 5. Get booking by PNR
//    @GetMapping("/pnr/{pnr}")
//    public ResponseEntity<BookingResponseDto> getBookingByPnr(@PathVariable String pnr) {
//        return ResponseEntity.ok ( bookingService.getBookingByPnr ( pnr ) );
//    }
//
//    // 6.Get all bookings for a specific user
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<BookingResponseDto>> getBookingsByUser(@PathVariable Long userId) {
//        return ResponseEntity.ok ( bookingService.getBookingsByUser ( userId ) );
//    }
//
//    //  7.Update payment status
//    @PutMapping("/payment-status/{pnr}")
//    public ResponseEntity<String> updatePaymentStatus(@PathVariable String pnr,
//                                                      @RequestParam String status) {
//        return ResponseEntity.ok ( bookingService.updatePaymentStatus ( pnr, status ) );
//    }
//
//    //  8.Get all bookings (admin use only)
//    @GetMapping("/all")
//    public ResponseEntity<List<Booking>> getAllBookings() {
//        return ResponseEntity.ok ( bookingService.getAllBookings () );
//    }
//
//    // 9. Get bookings by journey date (format: yyyy-MM-dd)
//    @GetMapping("/journey-date")
//    public ResponseEntity<List<BookingResponseDto>> getBookingsByJourneyDate(
//            @RequestParam String date) {
//        return ResponseEntity.ok ( bookingService.getBookingsByJourneyDate ( date ) );
//    }
//
//    // 10. Get booking status by PNR
//    @GetMapping("/status/{pnr}")
//    public ResponseEntity<String> getBookingStatus(@PathVariable String pnr) {
//        return ResponseEntity.ok(bookingService.getBookingStatus(pnr));
//    }
//
//    // 11. Get booking details by booking ID (admin/internal)
//    @GetMapping("/id/{bookingId}")
//    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) {
//        return bookingService.getBookingById(bookingId)
//                .map(ResponseEntity::ok)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
//    }
//
//
//    @PutMapping("/{id}/status")
//    public ResponseEntity<String> updateBookingStatus(
//            @PathVariable Long id,
//            @RequestParam String status) {
//        bookingService.updateBookingStatus(id, status);
//        return ResponseEntity.ok("Booking status updated to " + status);
//    }
//
//
//    // add after payment services
//    @PutMapping("/{id}/confirm-payment")
//    public ResponseEntity<String> confirmPayment(@PathVariable Long id) {
//        bookingService.confirmPayment(id);
//        return ResponseEntity.ok("Payment captured, booking confirmed");
//    }
//
//    @PostMapping("/{bookingId}/payment-confirmed")
//    public void paymentConfirmed(@PathVariable Long bookingId,
//                                 @RequestBody PaymentInfoDto dto) throws IOException {
//        bookingService.confirmBookingAndSendTicket(bookingId, dto.getPaymentId());
//    }
//
//    // BookingController.java
//    @PostMapping("/payment/confirm")
//    public ResponseEntity<String> confirmPayment(@RequestBody PaymentConfirmationDto dto)
//            throws IOException {
//        bookingService.confirmBookingAndSendTicket(dto.getBookingId(), dto.getPaymentId());
//        return ResponseEntity.ok("✅ Ticket generated & e‑mailed");
//    }
//
////    @KafkaListener(topics = "payment-success")
////    public void onPaymentSuccess(PaymentConfirmationDto dto) throws IOException {
////        confirmBookingAndSendTicket(dto.getBookingId(), dto.getPaymentId());
////
//
//    @PostMapping("/{id}/paid")
//    public void confirmPaid(@PathVariable Long id,
//                            @RequestBody PaymentInfoDto info) throws IOException {
//        bookingService.confirmBookingAndSendTicket(id, info.getPaymentId());
//    }
//
//    /* optional: by PNR instead of id */
//    @PostMapping("/pnr/{pnr}/paid")
//    public void confirmPaidByPnr(@PathVariable String pnr,
//                                 @RequestBody PaymentInfoDto info) throws IOException {
//        bookingService.confirmPaidByPnr(pnr, info.getPaymentId());
//    }
//
//
//
//}

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class BookingController {

    @Autowired
    private BookingService bookingService;

    private static final Logger log = LoggerFactory.getLogger ( BookingController.class );


    // 1. Book & Pay
    @PostMapping("/BookTicket")
    public ResponseEntity<BookingAndPaymentDto> createBooking(
            @RequestBody BookingRequestDto dto) throws IOException, InterruptedException {
        return ResponseEntity.ok(bookingService.bookTicket(dto));
    }

    // 2. Confirm payment (called by Payment-Service)
    @PostMapping("/{id}/payment-confirmed")
    public ResponseEntity<String> confirmPaid(
            @PathVariable("id") Long bookingId,
            @RequestBody PaymentInfoDto info) throws IOException {
        log.info("🛬 Received from Feign → bookingId: {}, paymentId: {}", bookingId, info.getPaymentId());
        bookingService.confirmBookingAndSendTicket(bookingId, info.getPaymentId());
        return ResponseEntity.ok("✅ Payment confirmed and ticket mailed");
    }


    // 3. Confirm payment by PNR (if needed)
    @PostMapping("/pnr/{pnr}/payment-confirmed")
    public ResponseEntity<String> confirmPaidByPnr(
            @PathVariable String pnr,
            @RequestBody PaymentInfoDto info) throws IOException {
        bookingService.confirmPaidByPnr(pnr, info.getPaymentId());
        return ResponseEntity.ok("✅ Payment confirmed by PNR");
    }

    // 4. Get booking by ID
    @GetMapping("/id/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
    }

    // 5. Get booking by PNR
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<BookingDto> getBookingByPnr(@PathVariable String pnr) {
        BookingDto booking = bookingService.getBookingById(Long.valueOf(pnr))
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnr));
        return ResponseEntity.ok(booking);
    }

}


