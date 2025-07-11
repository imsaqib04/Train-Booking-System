package com.saqib.Booking_Service.service;

import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.pdfUtill.PdfGenerator;
import com.saqib.Booking_Service.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailService emailService;

    // 3. generate ticket invoice
    public ByteArrayInputStream generateTicketPdf(Long bookingId) throws IOException {
        Booking booking = bookingRepository.findById ( bookingId )
                .orElseThrow ( () -> new RuntimeException ( "Booking not found" ) );

        return PdfGenerator.generateInvoice ( booking );
    }


    @Scheduled(fixedRate = 3600000 * 5) // Every hour
    public void sendJourneyReminders() {
        LocalDateTime now = LocalDateTime.now ();
        LocalDateTime within24hrs = now.plusHours ( 24 );

        List<Booking> upcoming = bookingRepository
                .findUpcomingJourneys ( now.toLocalDate (), within24hrs.toLocalDate () );

        for (Booking b : upcoming) {
            emailService.sendBookingMail (
                    b.getEmail (),
                    "Journey Reminder – PNR " + b.getPnrNumber (),
                    "Dear " + b.getName () + ",\n\n"
                            + "Your train " + b.getTrainName () + " departs tomorrow ("
                            + b.getJourneyDate () + ").\nHave a nice trip!" );
        }

    }
}
