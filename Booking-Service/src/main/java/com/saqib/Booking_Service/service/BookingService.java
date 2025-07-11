package com.saqib.Booking_Service.service;

import com.saqib.Booking_Service.enums.BookingStatus;
import com.saqib.Booking_Service.booking.utill.FareCalculator;
import com.saqib.Booking_Service.dto.*;
import com.saqib.Booking_Service.exceptions.ResourceNotFoundException;
import com.saqib.Booking_Service.feign.PaymentClient;
import com.saqib.Booking_Service.feign.TrainClient;
import com.saqib.Booking_Service.feign.UserClient;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.model.Passenger;
import com.saqib.Booking_Service.enums.PaymentStatus;
import com.saqib.Booking_Service.pdfUtill.PdfGenerator;
import com.saqib.Booking_Service.repo.BookingRepository;
import com.saqib.Booking_Service.repo.PassengerRepository;
import com.saqib.Booking_Service.repo.SeatRepository;
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

    private static final Logger log = LoggerFactory.getLogger ( BookingService.class );

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PaymentClient paymentClient;


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
        return String.format ( "%010d", Math.abs ( new Random ().nextLong () ) % 1_000_000_0000L );
    }

    private UserResponseDto fetchUser(Long userId) {
        try {
            return userClient.getUserById ( userId );
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException ( "User not found with ID: " + userId );
        } catch (FeignException ex) {
            log.error ( "❌ Failed to fetch user info from User Service: {}", ex.getMessage () );
            throw new RuntimeException ( "User Service unavailable" );
        }
    }

    private TrainDto fetchTrain(Long trainId) {
        try {
            return trainClient.getTrainById ( trainId );
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException ( "Train not found with ID: " + trainId );
        } catch (FeignException ex) {
            log.error ( "❌ Failed to fetch train info from Train Service: {}", ex.getMessage () );
            throw new RuntimeException ( "Train Service unavailable" );
        }
    }

    @Transactional
    public BookingResponseDto createPendingBooking(BookingRequestDto req) {

        // all validations & segment seat logic exactly as you already wrote …
        Booking booking = buildBooking ( req );

        /* critical: keep it PENDING until money comes in */
        booking.setStatus ( BookingStatus.WAITING );
        booking.setPaymentStatus ( PaymentStatus.PENDING );

        bookingRepository.save ( booking );
        passengerRepository.saveAll ( booking.getPassengers () );

        return BookingResponseDto.from ( booking );
    }



//    @Transactional
//    public BookingAndPaymentDto bookTicket(BookingRequestDto req) throws IOException, InterruptedException {
//
//        /* 0. meta */
//        TrainDto train = fetchTrain ( req.getTrainId () );
//        UserResponseDto user = fetchUser ( req.getUserId () );
//
//        /* 1. from / to stopNumber */
//        int fromStop = trainClient.getStopNumber ( req.getTrainId (), req.getFromStation () );
//        int toStop = trainClient.getStopNumber ( req.getTrainId (), req.getToStation () );
//        if (fromStop >= toStop)
//            throw new IllegalArgumentException ( "Stations out of order" );
//
//
//
//        /* 2. segment availability */
//        int paxCount = req.getPassengers ().size ();
//        Integer booked = bookingRepository.segmentSeats (
//                req.getTrainId (), req.getJourneyDate (), req.getCoachType (),
//                fromStop, toStop );               // may return null
//        int seatsBooked = booked == null ? 0 : booked;
//        int coachCapacity = switch (req.getCoachType ()) {
//            case SITTING -> train.getSeats2S ();
//            case SL -> train.getSeatsSl ();
//            case AC3 -> train.getSeats3Ac ();
//            case AC2 -> train.getSeats2Ac ();
//            case AC1 -> train.getSeats1Ac ();
//        };
//
//
//        if (seatsBooked + paxCount > coachCapacity)
//            throw new ResourceNotFoundException ( "Segment full" );
//
//        List<String> freeSeats = seatRepository.findFreeSeats (
//                req.getCoachType ().name (),
//                req.getTrainId (),
//                req.getJourneyDate (),
//                fromStop, toStop,
//                paxCount );
//
//        if (freeSeats.size () < paxCount)
//            throw new ResourceNotFoundException ( "No seats free for this segment" );
//
//
//        for (int i = 0; i < paxCount; i++) {
//            req.getPassengers().get(i).setSeatNo( freeSeats.get(i) );
//        }
//
//        SeatUpdateRequest seatUpdate = new SeatUpdateRequest ();
//        seatUpdate.setCoachClass ( req.getCoachType () );
//        seatUpdate.setSeats ( paxCount );
//
//        trainClient.reduceSeats ( req.getTrainId (), seatUpdate );
//
//        /* 4. build Booking entity */
//        Booking booking = new Booking ();
//        booking.setUserId ( req.getUserId () );
//        booking.setTrainId ( req.getTrainId () );
//        booking.setCoachType ( req.getCoachType () );
//        booking.setJourneyDate ( req.getJourneyDate () );
//        booking.setBookingSource ( req.getBookingSource () );
//        booking.setBookingMode ( req.getBookingMode () );
//        booking.setBookingTime ( LocalDateTime.now () );
//        booking.setPnrNumber ( generatePNR () );
//        booking.setStatus ( BookingStatus.CONFIRMED );
//        booking.setPaymentStatus ( PaymentStatus.PENDING );
//
//        booking.setName ( user.getFullName () );
//        booking.setEmail ( user.getEmail () );
//        booking.setTrainName ( train.getTrainName () );
//        booking.setSource ( req.getFromStation () );
//        booking.setDestination ( req.getToStation () );
//        booking.setFromStopNumber ( fromStop );
//        booking.setToStopNumber ( toStop );
//
//        /* 5. passengers & fare */
//        List<Passenger> pax = new ArrayList<> ();
//        for (PassengerRequestDto pdto : req.getPassengers ()) {
//            Passenger p = new Passenger ();
//            p.setName ( pdto.getName () );
//            p.setAge ( pdto.getAge () );
//            p.setGender ( pdto.getGender () );
//            p.setIdProofType ( pdto.getIdProofType () );
//            p.setSeatPreference ( pdto.getSeatPreference () );
//            p.setChild ( pdto.isChild () );
//            p.setSeniorCitizen ( pdto.isSeniorCitizen () );
//            p.setFare ( fareCalculator.calculateFare ( p, req.getCoachType ().name () ) );
//            p.setBooking ( booking );
//            pax.add ( p );
//        }
//        booking.setPassengers ( pax );
//        booking.setTotalSeats ( paxCount );
//        booking.setTotalFare ( pax.stream ().mapToDouble ( Passenger::getFare ).sum () );
//
//        /* 6. persist */
////        bookingRepository.save ( booking );
////        passengerRepository.saveAll ( pax );
//        bookingRepository.saveAndFlush(booking);
//        passengerRepository.saveAll(pax); // for passengers
//
//        if (booking.getBookingId() == null) {
//            throw new IllegalStateException("Booking was not saved properly, ID is null");
//        }
//
//
//        // — create Razorpay order through Payment-Service —
//        PaymentRequestDto payReq = new PaymentRequestDto(
//                booking.getBookingId(),         // may be null (पहले save हुआ)
//                booking.getPnrNumber(),         // ⭐️ always non‑null
//                "INR");
//
//        Thread.sleep(300); // 300ms pause to let DB sync
//        PaymentResponseDto payment = paymentClient.createOrder(payReq);
//
//
//        return new BookingAndPaymentDto(
//                BookingResponseDto.from(booking),
//                payment
//        );
//    }

    @Transactional
    public void cancelBooking(Long bookingId, String reason) {
        Booking booking = bookingRepository.findById ( bookingId )
                .orElseThrow ( () -> new ResourceNotFoundException ( "Booking not found" ) );

        booking.setStatus ( BookingStatus.CANCELLED );
        booking.setCancellationDate ( LocalDateTime.now () );
        booking.setCancellationReason ( reason );
        bookingRepository.save ( booking );

        int cancelledSeats = booking.getTotalSeats ();
        List<Booking> waitingList = bookingRepository.findWaitingList ( booking.getTrainId (), booking.getJourneyDate () );

        int confirmedSeats = 0;
        for (Booking wait : waitingList) {
            if (wait.getTotalSeats () <= cancelledSeats) {
                wait.setStatus ( BookingStatus.CONFIRMED );
                wait.setWaitingListPosition ( null );
                bookingRepository.save ( wait );
                cancelledSeats -= wait.getTotalSeats ();
                confirmedSeats += wait.getTotalSeats ();

                try {
                    UserResponseDto user = fetchUser ( wait.getUserId () );
                    emailService.sendBookingMail (
                            user.getEmail (),
                            "✅ Ticket Auto-Confirmed",
                            "PNR: " + wait.getPnrNumber () + " has been confirmed after cancellation."
                    );
                } catch (Exception ex) {
                    log.warn ( "⚠️ Could not fetch user for waitlist promotion: {}", ex.getMessage () );
                }
            }
        }

        SeatUpdateRequest seatReq = new SeatUpdateRequest ();
        seatReq.setCoachClass ( booking.getCoachType () );
        seatReq.setSeats ( cancelledSeats );
        trainClient.increaseSeats ( booking.getTrainId (), seatReq );

        log.info ( "✅ Auto-confirmed {} seat(s)", confirmedSeats );
    }

    @Transactional
    public String cancelBooking(String pnr, String reason) {
        Booking booking = bookingRepository.findByPnrNumber ( pnr )
                .orElseThrow ( () -> new ResourceNotFoundException ( "PNR not found" ) );

        if (booking.getStatus () != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException ( "Only CONFIRMED bookings can be cancelled" );
        }

        List<Booking> groupBookings = bookingRepository.findByUserIdAndTrainIdAndJourneyDate (
                booking.getUserId (), booking.getTrainId (), booking.getJourneyDate ()
        );

        int totalCancelled = 0;
        for (Booking b : groupBookings) {
            if (b.getStatus () == BookingStatus.CONFIRMED) {
                b.setStatus ( BookingStatus.CANCELLED );
                b.setCancellationDate ( LocalDateTime.now () );
                b.setCancellationReason ( reason );
                totalCancelled += b.getTotalSeats ();
            }
        }


        SeatUpdateRequest seatReq = new SeatUpdateRequest ();
        seatReq.setCoachClass ( booking.getCoachType () );
        seatReq.setSeats ( totalCancelled );
        trainClient.increaseSeats ( booking.getTrainId (), seatReq );


        List<Booking> waitingList = bookingRepository.findWaitingList ( booking.getTrainId (), booking.getJourneyDate () );
        for (Booking wait : waitingList) {
            if (wait.getTotalSeats () <= totalCancelled) {
                wait.setStatus ( BookingStatus.CONFIRMED );
                wait.setWaitingListPosition ( null );
                bookingRepository.save ( wait );
                totalCancelled -= wait.getTotalSeats ();

                try {
                    UserResponseDto user = fetchUser ( wait.getUserId () );
                    emailService.sendBookingMail (
                            user.getEmail (),
                            "✅ Ticket Auto-Confirmed",
                            "PNR: " + wait.getPnrNumber () + " has been auto-confirmed."
                    );
                } catch (Exception e) {
                    log.warn ( "⚠️ Failed to send auto-confirm email: {}", e.getMessage () );
                }
            }
        }

        try {
            UserResponseDto user = fetchUser ( booking.getUserId () );
            emailService.sendBookingMail (
                    user.getEmail (),
                    "🚫 Ticket Cancelled - PNR: " + booking.getPnrNumber (),
                    "Dear " + user.getFullName () + ",\n\nYour ticket has been cancelled.\nReason: " + reason
            );
        } catch (Exception e) {
            log.warn ( "⚠️ Failed to send cancellation email: {}", e.getMessage () );
        }

        return "🚫 Booking cancelled for PNR: " + booking.getPnrNumber ();
    }

    public BookingResponseDto getBookingByPnr(String pnr) {
        Booking booking = bookingRepository.findByPnrNumber ( pnr )
                .orElseThrow ( () -> new ResourceNotFoundException ( "❌ PNR not found" ) );

        if (booking.getStatus () == BookingStatus.CANCELLED) {
            log.warn ( "⚠️ Booking with PNR {} is cancelled: {}", pnr, booking.getCancellationReason () );
        }

        BookingResponseDto dto = BookingResponseDto.from ( booking );

        try {
            UserResponseDto user = fetchUser ( booking.getUserId () );
            dto.setName ( user.getFullName () );
            dto.setEmail ( user.getEmail () );
        } catch (Exception ex) {
            log.error ( "❌ Failed to fetch user info: {}", ex.getMessage () );
        }

        try {
            TrainDto train = fetchTrain ( booking.getTrainId () );
            dto.setTrainName ( train.getTrainName () );
            dto.setSource ( train.getSource () );
            dto.setDestination ( train.getDestination () );
        } catch (Exception ex) {
            log.error ( "❌ Failed to fetch train info: {}", ex.getMessage () );
        }

        return dto;
    }

    public List<BookingResponseDto> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId ( userId ).stream ()
                .map ( BookingResponseDto::from )
                .collect ( Collectors.toList () );
    }

    public String updatePaymentStatus(String pnr, String status) {
        Booking booking = bookingRepository.findByPnrNumber ( pnr )
                .orElseThrow ( () -> new ResourceNotFoundException ( "PNR not found" ) );

        booking.setPaymentStatus ( PaymentStatus.valueOf ( status.toUpperCase () ) );
        bookingRepository.save ( booking );
        log.info ( "💰 Payment status updated for PNR {}", pnr );
        return "💰 Payment status updated";
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll ();
    }

    public List<BookingResponseDto> getBookingsByJourneyDate(String date) {
        LocalDate journeyDate = LocalDate.parse ( date );
        return bookingRepository.findByJourneyDate ( journeyDate ).stream ()
                .map ( BookingResponseDto::from )
                .collect ( Collectors.toList () );
    }

    public String getBookingStatus(String pnr) {
        Booking booking = bookingRepository.findByPnrNumber ( pnr )
                .orElseThrow ( () -> new ResourceNotFoundException ( "PNR not found" ) );
        return booking.getStatus ().toString ();
    }
    public Optional<BookingDto> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(BookingDto::from); // ✅ This is correct
    }


    public void updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById ( bookingId )
                .orElseThrow ( () -> new RuntimeException ( "Booking not found" ) );
        booking.setStatus ( BookingStatus.valueOf ( status ) ); // e.g., "CONFIRMED"
        bookingRepository.save ( booking );
    }


    // add after payment services
    @Transactional
    public void confirmPayment(Long bookingId) {
        Booking b = bookingRepository.findById ( bookingId )
                .orElseThrow ( () -> new ResourceNotFoundException ( "Booking not found" ) );

        b.setPaymentStatus ( PaymentStatus.SUCCESS );
        b.setStatus ( BookingStatus.CONFIRMED );


        bookingRepository.save ( b );

        log.info ( "💰 Payment captured, booking CONFIRMED for id {}", bookingId );
    }

    /*──────────────────────────────────────────────────────────────
      STEP‑2  – Payment‑Service notifies us → confirm & send ticket
     ─────────────────────────────────────────────────────────────*/
    @Transactional
    public void confirmBookingAndSendTicket(Long bookingId, String paymentId)
            throws IOException {


        log.info("📦 Setting paymentId to: {}", paymentId);

        Booking b = bookingRepository.findById ( bookingId )
                .orElseThrow ( () -> new ResourceNotFoundException ( "Booking not found" ) );

        if (b.getPaymentStatus () == PaymentStatus.SUCCESS) return; // idempotent

        b.setStatus ( BookingStatus.CONFIRMED );
        b.setPaymentStatus ( PaymentStatus.SUCCESS );
        log.info("📦 Setting paymentId to: {}", paymentId);
        b.setPaymentId ( paymentId );
        bookingRepository.saveAndFlush(b);


        ByteArrayInputStream pdf = PdfGenerator.generateInvoice ( b );

        emailService.sendBookingMailWithAttachment (
                b.getEmail (),
                "Your e‑Ticket – PNR " + b.getPnrNumber (),
                "Dear " + b.getName () + ",\n\n" +
                        "Your payment was successful and your booking is confirmed.\n" +
                        "Please find your ticket attached.\n\n" +
                        "Happy journey!",
                pdf.readAllBytes () );

        log.info ( "🎫 Ticket mailed for booking {}", bookingId );
    }

    public void confirmPaidByPnr(String pnr, String paymentId) throws IOException {
        Booking b = bookingRepository.findByPnrNumber ( pnr )
                .orElseThrow ( () -> new ResourceNotFoundException ( "PNR not found" ) );
        confirmBookingAndSendTicket ( b.getBookingId (), paymentId );
    }

    /*──────────────────────────────────────────────────────────────
  Helper: converts PassengerRequestDto → Passenger entity
 ─────────────────────────────────────────────────────────────*/
    private List<Passenger> mapPassengers(List<PassengerRequestDto> dtos,
                                          Booking booking
                                          ) {


        List<Passenger> list = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            PassengerRequestDto src = dtos.get(i);
            Passenger p = new Passenger();

            /* basic fields */
            p.setSeatNo(src.getSeatNo());   // ← add this
            p.setName(src.getName());
            p.setAge(src.getAge());
            p.setGender(src.getGender());
            p.setIdProofType(src.getIdProofType());
            p.setSeatPreference(src.getSeatPreference());
            p.setChild(src.isChild());
            p.setSeniorCitizen(src.isSeniorCitizen());

            /* fare calculation */
            double fare = fareCalculator.calculateFare(p,
                    booking.getCoachType().name());
            p.setFare(fare);

            p.setBooking(booking);       // ← parent‑side
            list.add(p);
        }
        return list;
    }


    /*──────────────────────────────────────────────────────────────
      INTERNAL helper – builds Booking entity with all fields set
     ─────────────────────────────────────────────────────────────*/
    private Booking buildBooking(BookingRequestDto req) {

        TrainDto train = fetchTrain ( req.getTrainId () );
        UserResponseDto user = fetchUser ( req.getUserId () );

        // … seat‑availability checks exactly as you had …

        Booking booking = new Booking ();
        booking.setUserId ( req.getUserId () );
        booking.setTrainId ( req.getTrainId () );
        booking.setCoachType ( req.getCoachType () );
        booking.setJourneyDate ( req.getJourneyDate () );
        booking.setBookingSource ( req.getBookingSource () );
        booking.setBookingMode ( req.getBookingMode () );
        booking.setBookingTime ( LocalDateTime.now () );
        booking.setPnrNumber ( generatePNR () );

        booking.setName ( user.getFullName () );
        booking.setEmail ( user.getEmail () );
        booking.setTrainName ( train.getTrainName () );
        booking.setSource ( req.getFromStation () );
        booking.setDestination ( req.getToStation () );

        /* passengers + fare */
        List<Passenger> pax = mapPassengers ( req.getPassengers (), booking);
        booking.setPassengers ( pax );
        booking.setTotalSeats ( pax.size () );
        booking.setTotalFare ( pax.stream ().mapToDouble ( Passenger::getFare ).sum () );

        return booking;
    }
    @Transactional
    public Booking saveBookingAndPassengers(BookingRequestDto req, TrainDto train, UserResponseDto user) {
        // Build Booking object
        Booking booking = buildBooking(req);
        booking.setStatus(BookingStatus.WAITING);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        bookingRepository.saveAndFlush(booking);
        passengerRepository.saveAll(booking.getPassengers());

        return booking;
    }
    public BookingAndPaymentDto bookTicket(BookingRequestDto req) throws IOException, InterruptedException {
        TrainDto train = fetchTrain(req.getTrainId());
        UserResponseDto user = fetchUser(req.getUserId());

        Booking booking = saveBookingAndPassengers(req, train, user);

        Thread.sleep(500); // wait for DB to sync 🔥🔥🔥

        PaymentRequestDto payReq = new PaymentRequestDto(
                booking.getBookingId(),
                booking.getPnrNumber(),
                "INR"
        );

        PaymentResponseDto payment = paymentClient.createOrder(payReq);

        return new BookingAndPaymentDto(
                BookingResponseDto.from(booking),
                payment
        );
    }

}