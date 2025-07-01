package com.saqib.Booking_Service.service;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.booking.utill.FareCalculator;
import com.saqib.Booking_Service.dto.*;
import com.saqib.Booking_Service.exceptions.ResourceNotFoundException;
import com.saqib.Booking_Service.feign.TrainClient;
import com.saqib.Booking_Service.feign.UserClient;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.model.Passenger;
import com.saqib.Booking_Service.model.PaymentStatus;
import com.saqib.Booking_Service.pdfUtill.PdfGenerator;
import com.saqib.Booking_Service.repo.BookingRepository;
import com.saqib.Booking_Service.repo.PassengerRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserClient userClient;

    private final BookingRepository bookingRepository;
    private final TrainClient trainClient;
    private final PassengerRepository passengerRepository;
    private final FareCalculator fareCalculator;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          TrainClient trainClient,
                          PassengerRepository passengerRepository,
                          FareCalculator fareCalculator) {
        this.bookingRepository = bookingRepository;
        this.trainClient = trainClient;
        this.passengerRepository = passengerRepository;
        this.fareCalculator = fareCalculator;
    }

    private String generatePNR() {
        return String.format("%010d", Math.abs(new Random().nextLong()) % 1_000_000_0000L);
    }

    private UserResponseDto fetchUser(Long userId) {
        try {
            return userClient.getUserById(userId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        } catch (FeignException ex) {
            log.error("❌ Failed to fetch user info from User Service: {}", ex.getMessage());
            throw new RuntimeException("User Service unavailable");
        }
    }

    private TrainDto fetchTrain(Long trainId) {
        try {
            return trainClient.getTrainById(trainId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Train not found with ID: " + trainId);
        } catch (FeignException ex) {
            log.error("❌ Failed to fetch train info from Train Service: {}", ex.getMessage());
            throw new RuntimeException("Train Service unavailable");
        }
    }

    @Transactional
    public BookingResponseDto bookTicket(BookingRequestDto request) throws IOException {

        /* 1. validate passenger count & seat availability */
        TrainDto train = fetchTrain(request.getTrainId());
        if (train.getAvailableSeats() < request.getPassengers().size())
            throw new ResourceNotFoundException("Not enough seats");

        trainClient.updateAvailableSeats(train.getTrainId(), request.getPassengers().size());

        /* 2. base booking */
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setTrainId(request.getTrainId());
        booking.setCoachType(request.getCoachType());
        booking.setJourneyDate(request.getJourneyDate());
        booking.setBookingSource(request.getBookingSource());
        booking.setBookingMode(request.getBookingMode());
        booking.setBookingTime(LocalDateTime.now());
        booking.setPnrNumber(generatePNR());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        /* --- NEW: hydrate with user & train info --- */
        UserResponseDto user = fetchUser(request.getUserId());
        booking.setEmail (user.getEmail());

        booking.setTrainName(train.getTrainName());
        booking.setSource (train.getSource());
        booking.setDestination (train.getDestination());

        /* 3. passengers + fare */
        List<Passenger> pax = mapPassengers(request.getPassengers(), booking);
        booking.setPassengers(pax);
        booking.setTotalFare(pax.stream().mapToDouble(Passenger::getFare).sum());
        booking.setTotalSeats(pax.size());

        bookingRepository.save(booking);
        passengerRepository.saveAll(pax);

        /* 4. send temp confirmation mail (optional) */

        /* 5. response dto */
        return BookingResponseDto.from(booking);
    }

    /* ------------------- confirmPayment callback ------------------- */
    @Transactional
    public void confirmBookingAndSendTicket(Long bookingId, String paymentId) {

        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (b.getStatus() == BookingStatus.CONFIRMED && b.getPaymentStatus() == PaymentStatus.SUCCESS)
            return;   // already processed

        b.setStatus(BookingStatus.CONFIRMED);
        b.setPaymentStatus(PaymentStatus.SUCCESS);
        b.setPaymentId(paymentId);
        bookingRepository.save(b);

        // --- generate PDF ---
        ByteArrayInputStream pdf = PdfGenerator.generateInvoice(b);

        // --- email ---
        String subj = "Your Ticket - PNR " + b.getPnrNumber();
        String body = "Dear "+ b.getPassengers().get(0).getName()
                +",\n\nYour booking is confirmed.\nHappy Journey!";
        emailService.sendBookingMailWithAttachment(
                b.getEmail (), subj, body, pdf.readAllBytes());
    }

    /* helper method to map passengers & calc fare */
    private List<Passenger> mapPassengers(List<PassengerRequestDto> req, Booking booking) {
        List<Passenger> list = new ArrayList<>();
        for (PassengerRequestDto dto : req) {
            Passenger p = new Passenger();
            p.setName(dto.getName());
            p.setAge(dto.getAge());
            p.setGender(dto.getGender());
            p.setIdProofType(dto.getIdProofType());
            p.setSeatPreference(dto.getSeatPreference());
            p.setChild(dto.isChild());
            p.setSeniorCitizen(dto.isSeniorCitizen());
            p.setFare(fareCalculator.calculateFare(p, String.valueOf ( booking.getCoachType() ) ));
            p.setBooking(booking);
            list.add(p);
        }
        return list;
    }

    @Transactional
    public void cancelBooking(Long bookingId, String reason) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationDate(LocalDateTime.now());
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);

        int cancelledSeats = booking.getTotalSeats();
        List<Booking> waitingList = bookingRepository.findWaitingList(booking.getTrainId(), booking.getJourneyDate());

        int confirmedSeats = 0;
        for (Booking wait : waitingList) {
            if (wait.getTotalSeats() <= cancelledSeats) {
                wait.setStatus(BookingStatus.CONFIRMED);
                wait.setWaitingListPosition(null);
                bookingRepository.save(wait);
                cancelledSeats -= wait.getTotalSeats();
                confirmedSeats += wait.getTotalSeats();

                try {
                    UserResponseDto user = fetchUser(wait.getUserId());
                    emailService.sendBookingMail(
                            user.getEmail(),
                            "✅ Ticket Auto-Confirmed",
                            "PNR: " + wait.getPnrNumber() + " has been confirmed after cancellation."
                    );
                } catch (Exception ex) {
                    log.warn("⚠️ Could not fetch user for waitlist promotion: {}", ex.getMessage());
                }
            }
        }

        if (cancelledSeats > 0) {
            trainClient.increaseAvailableSeats(booking.getTrainId(), cancelledSeats);
        }

        log.info("✅ Auto-confirmed {} seat(s)", confirmedSeats);
    }

    @Transactional
    public String cancelBooking(String pnr, String reason) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("PNR not found"));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only CONFIRMED bookings can be cancelled");
        }

        List<Booking> groupBookings = bookingRepository.findByUserIdAndTrainIdAndJourneyDate(
                booking.getUserId(), booking.getTrainId(), booking.getJourneyDate()
        );

        int totalCancelled = 0;
        for (Booking b : groupBookings) {
            if (b.getStatus() == BookingStatus.CONFIRMED) {
                b.setStatus(BookingStatus.CANCELLED);
                b.setCancellationDate(LocalDateTime.now());
                b.setCancellationReason(reason);
                totalCancelled += b.getTotalSeats();
            }
        }

        bookingRepository.saveAll(groupBookings);
        trainClient.increaseAvailableSeats(booking.getTrainId(), totalCancelled);

        List<Booking> waitingList = bookingRepository.findWaitingList(booking.getTrainId(), booking.getJourneyDate());
        for (Booking wait : waitingList) {
            if (wait.getTotalSeats() <= totalCancelled) {
                wait.setStatus(BookingStatus.CONFIRMED);
                wait.setWaitingListPosition(null);
                bookingRepository.save(wait);
                totalCancelled -= wait.getTotalSeats();

                try {
                    UserResponseDto user = fetchUser(wait.getUserId());
                    emailService.sendBookingMail(
                            user.getEmail(),
                            "✅ Ticket Auto-Confirmed",
                            "PNR: " + wait.getPnrNumber() + " has been auto-confirmed."
                    );
                } catch (Exception e) {
                    log.warn("⚠️ Failed to send auto-confirm email: {}", e.getMessage());
                }
            }
        }

        try {
            UserResponseDto user = fetchUser(booking.getUserId());
            emailService.sendBookingMail(
                    user.getEmail(),
                    "🚫 Ticket Cancelled - PNR: " + booking.getPnrNumber(),
                    "Dear " + user.getFullName() + ",\n\nYour ticket has been cancelled.\nReason: " + reason
            );
        } catch (Exception e) {
            log.warn("⚠️ Failed to send cancellation email: {}", e.getMessage());
        }

        return "🚫 Booking cancelled for PNR: " + booking.getPnrNumber();
    }

    public BookingResponseDto getBookingByPnr(String pnr) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("❌ PNR not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            log.warn("⚠️ Booking with PNR {} is cancelled: {}", pnr, booking.getCancellationReason());
        }

        BookingResponseDto dto = BookingResponseDto.from(booking);

        try {
            UserResponseDto user = fetchUser(booking.getUserId());
            dto.setName(user.getFullName());
            dto.setEmail(user.getEmail());
        } catch (Exception ex) {
            log.error("❌ Failed to fetch user info: {}", ex.getMessage());
        }

        try {
            TrainDto train = fetchTrain(booking.getTrainId());
            dto.setTrainName(train.getTrainName());
            dto.setSource(train.getSource());
            dto.setDestination(train.getDestination());
        } catch (Exception ex) {
            log.error("❌ Failed to fetch train info: {}", ex.getMessage());
        }

        return dto;
    }

    public List<BookingResponseDto> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(BookingResponseDto::from)
                .collect(Collectors.toList());
    }

    public String updatePaymentStatus(String pnr, String status) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("PNR not found"));

        booking.setPaymentStatus(PaymentStatus.valueOf(status.toUpperCase()));
        bookingRepository.save(booking);
        log.info("💰 Payment status updated for PNR {}", pnr);
        return "💰 Payment status updated";
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<BookingResponseDto> getBookingsByJourneyDate(String date) {
        LocalDate journeyDate = LocalDate.parse(date);
        return bookingRepository.findByJourneyDate(journeyDate).stream()
                .map(BookingResponseDto::from)
                .collect(Collectors.toList());
    }

    public String getBookingStatus(String pnr) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("PNR not found"));
        return booking.getStatus().toString();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public void updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus( BookingStatus.valueOf ( status ) ); // e.g., "CONFIRMED"
        bookingRepository.save(booking);
    }


    // add after payment services
    @Transactional
    public void confirmPayment(Long bookingId) {
        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        b.setPaymentStatus(PaymentStatus.SUCCESS);
        b.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(b);

        log.info("💰 Payment captured, booking CONFIRMED for id {}", bookingId);
    }
//
//    @Transactional
//    public void confirmBookingAndSendTicket(Long bookingId, String paymentId) {
//
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found " + bookingId));
//
//        if (booking.getStatus() == BookingStatus.CONFIRMED) {
//            return;                     // already done – ignore duplicate
//        }
//
//        booking.setStatus(BookingStatus.CONFIRMED);
//        booking.setPaymentId(paymentId);
//        bookingRepository.save(booking);
//
//        // === Build PDF invoice ===
//        ByteArrayInputStream pdfBytes = PdfGenerator.generateInvoice(booking);
//
//        // === Send e‑mail ===
//        String subject = "Your Train Ticket – PNR " + booking.getPnrNumber();
//        String body    = "Dear " + (booking.getPassengers ()) + ",\n\n"
//                + "Your booking is confirmed. Please find your e‑ticket attached.\n\n"
//                + "Happy Journey!";
//        emailService.sendBookingMailWithAttachment(
//                booking.getEmail (), subject, body, pdfBytes.readAllBytes ());
//    }
}
