package com.saqib.Booking_Service.service;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.dto.BookingRequest;
import com.saqib.Booking_Service.dto.Train;
import com.saqib.Booking_Service.feign.TrainClient;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TrainClient trainClient;

    private static final int MAX_TRAIN_SEATS = 100;

//    public Booking bookTicket(BookingRequest request) {
//        if (request.getNumberOfSeats() < 1 || request.getNumberOfSeats() > 6) {
//            throw new IllegalArgumentException("You can book between 1 to 6 seats only.");
//        }
//
//        int availableSeats = getAvailableSeats(request.getTrainId());
//
//        Booking booking = new Booking();
//        booking.setUserId(request.getUserId());
//        booking.setTrainId(request.getTrainId());
//        booking.setNumberOfSeats(request.getNumberOfSeats());
//        booking.setBookingDate(LocalDate.now());
//        booking.setTotalFare(calculateFare(request.getNumberOfSeats()));
//
//        if (availableSeats >= booking.getNumberOfSeats()) {
//            booking.setStatus(BookingStatus.CONFIRMED);
//        } else {
//            booking.setStatus(BookingStatus.WAITING);
//        }
//
//        return bookingRepository.save(booking);
//    }

//    public Booking createBooking(BookingRequest bookingRequest) {
//        if (bookingRequest.getNumberOfSeats() <= 0) {
//            throw new IllegalArgumentException("Number of seats must be greater than 0");
//        }
//
//        Booking booking = new Booking();
//        booking.setUserId(bookingRequest.getUserId());
//        booking.setTrainId(bookingRequest.getTrainId());
//        booking.setNumberOfSeats(bookingRequest.getNumberOfSeats());
//        booking.setBookingDate(LocalDate.now());
//        booking.setStatus(BookingStatus.CONFIRMED);
//        booking.setTotalFare(calculateFare(bookingRequest.getNumberOfSeats()));
//
//        return bookingRepository.save(booking);
//    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId ( userId );
    }

    public String cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById ( bookingId );
        if (bookingOpt.isPresent ()) {
            Booking booking = bookingOpt.get ();
            booking.setStatus ( BookingStatus.CANCELLED );
            bookingRepository.save ( booking );
            return "Booking cancelled successfully.";
        }
        return "Booking not found.";
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll ();
    }

    private double calculateFare(int numberOfSeats) {
        return numberOfSeats * 150.0;
    }

    public int getAvailableSeats(Long trainId) {
        return trainClient.getAvailableSeats ( trainId );
    }

//    public Booking bookTicket(BookingRequest request) {
//        if (request.getNumberOfSeats() < 1 || request.getNumberOfSeats() > 6) {
//            throw new IllegalArgumentException("You can book between 1 to 6 seats only.");
//        }
//
//        int availableSeats = getAvailableSeats(request.getTrainId());
//
//        Booking booking = new Booking();
//        booking.setUserId(request.getUserId());
//        booking.setTrainId(request.getTrainId());
//        booking.setNumberOfSeats(request.getNumberOfSeats());
//        booking.setBookingDate(LocalDate.now());
//        booking.setTotalFare(calculateFare(request.getNumberOfSeats()));
//
//        if (availableSeats >= booking.getNumberOfSeats()) {
//            booking.setStatus(BookingStatus.CONFIRMED);
//        } else {
//            booking.setStatus(BookingStatus.WAITING);
//        }

    // Save booking to DB
//        Booking savedBooking = bookingRepository.save(booking);
//
//        // Fetch train details and attach to savedBooking (transient field)
//        Train train = trainClient.getTrainById(booking.getTrainId());
//        savedBooking.setTrain(train); // This works if `train` is marked @Transient in Booking entity
//
//        return savedBooking;
//    }


//    public Booking bookTicket(BookingRequest request) {
//        if (request.getNumberOfSeats() < 1 || request.getNumberOfSeats() > 6) {
//            throw new IllegalArgumentException("You can book between 1 to 6 seats only.");
//        }
//
//        int availableSeats = getAvailableSeats(request.getTrainId());
//
//        // Check if there are enough seats available
//        if (availableSeats < request.getNumberOfSeats()) {
//            throw new IllegalArgumentException("Not enough available seats.");
//        }
//
//        // Reduce available seats by the number of booked seats
//        trainClient.updateAvailableSeats(request.getTrainId(), availableSeats - request.getNumberOfSeats());
//
//        Booking booking = new Booking();
//        booking.setUserId(request.getUserId());
//        booking.setTrainId(request.getTrainId());
//        booking.setNumberOfSeats(request.getNumberOfSeats());
//        booking.setBookingDate(LocalDate.now());
//        booking.setTotalFare(calculateFare(request.getNumberOfSeats()));
//
//        booking.setStatus(availableSeats >= request.getNumberOfSeats() ? BookingStatus.CONFIRMED : BookingStatus.WAITING);
//
//        return bookingRepository.save(booking);
//    }

    //    public Booking bookTicket(BookingRequest request) {
//        if (request.getNumberOfSeats() < 1 || request.getNumberOfSeats() > 6) {
//            throw new IllegalArgumentException("You can book between 1 to 6 seats only.");
//        }
//
//        int availableSeats = getAvailableSeats(request.getTrainId());
//
//        Booking booking = new Booking();
//        booking.setUserId(request.getUserId());
//        booking.setTrainId(request.getTrainId());
//        booking.setNumberOfSeats(request.getNumberOfSeats());
//        booking.setBookingDate(LocalDate.now());
//        booking.setTotalFare(calculateFare(request.getNumberOfSeats()));
//
//        if (availableSeats >= booking.getNumberOfSeats()) {
//            booking.setStatus(BookingStatus.CONFIRMED);
//        } else {
//            booking.setStatus(BookingStatus.WAITING);
//        }
//
//        // Save booking first
//        Booking savedBooking = bookingRepository.save(booking);
//
//        // Fetch train details after booking is saved
//        Train train = trainClient.getTrainById(savedBooking.getTrainId());
//
//        // Set the train details in the booking
//        savedBooking.setTrain(train);
//
//        // Save the updated booking with the train information
//        return bookingRepository.save(savedBooking);
//    }
    public Booking bookTicket(BookingRequest request) {
        if (request.getNumberOfSeats () < 1 || request.getNumberOfSeats () > 6) {
            throw new IllegalArgumentException ( "You can book between 1 to 6 seats only." );
        }

        int availableSeats = getAvailableSeats ( request.getTrainId () );

        Booking booking = new Booking ();
        booking.setUserId ( request.getUserId () );
        booking.setTrainId ( request.getTrainId () );
        booking.setNumberOfSeats ( request.getNumberOfSeats () );
        booking.setBookingDate ( LocalDate.now () );
        booking.setTotalFare ( calculateFare ( request.getNumberOfSeats () ) );

        if (availableSeats >= booking.getNumberOfSeats ()) {
            booking.setStatus ( BookingStatus.CONFIRMED );
        } else {
            booking.setStatus ( BookingStatus.WAITING );
        }

        // Fetch train details before saving
        Train train = trainClient.getTrainById ( request.getTrainId () );
        booking.setTravleDate ( train.getTravelDate () ); // ✅ Set travel date from Train

        // Save booking
        Booking savedBooking = bookingRepository.save ( booking );

        // Optional: attach train details to response (if marked @Transient)
        savedBooking.setTrain ( train );

        return savedBooking;
    }
//    public int getBookedSeatsByTrainId(Long trainId) {
//        // Get all bookings for this train
//        List<Booking> bookings = bookingRepository.findByTrainId(trainId);
//
//        // Sum up the booked seats
//        int bookedSeats = bookings.stream()
//                .mapToInt(Booking::getNumberOfSeats)
//                .sum();
//
//        return bookedSeats;
//    }

    public int getBookedSeatsByTrainId(Long trainId) {
        return bookingRepository.countByTrainId ( trainId ); // Assuming countByTrainId is implemented in your repository
    }

    public Booking createBooking(BookingRequest request) {
        Train train = trainClient.getTrainById ( request.getTrainId () );

        int alreadyBookedSeats = bookingRepository.getTotalBookedSeatsByTrainId ( request.getTrainId () );
        int availableSeats = train.getTotalSeats () - alreadyBookedSeats;

        Booking booking = new Booking ();
        booking.setTrainId ( request.getTrainId () );
        booking.setUserId ( request.getUserId () );
        booking.setNumberOfSeats ( request.getNumberOfSeats () );
        booking.setBookingDate ( LocalDate.from ( LocalDateTime.now () ) );

        if (availableSeats >= request.getNumberOfSeats ()) {
            booking.setStatus ( BookingStatus.CONFIRMED );
        } else {
            booking.setStatus ( BookingStatus.WAITING );
        }

        return bookingRepository.save ( booking );
    }

//    public Booking processBooking(BookingRequest bookingRequest) {
//        // Fetch the train details using the trainId
//        Train train = trainClient.getTrainById(bookingRequest.getTrainId());
////        if (train == null) {
////            throw new TrainNotFoundException("Train not found with ID: " + bookingRequest.getTrainId());
////        }
//
//        // Get the number of already booked seats for the train
//        int alreadyBookedSeats = bookingRepository.getTotalBookedSeatsByTrainId(bookingRequest.getTrainId());
//        int availableSeats = train.getTotalSeats() - alreadyBookedSeats;
//
//        // Create a new booking entity
//        Booking booking = new Booking();
//        booking.setTrainId(bookingRequest.getTrainId());
//        booking.setUserId(bookingRequest.getUserId());
//        booking.setNumberOfSeats(bookingRequest.getNumberOfSeats());
//        booking.setBookingDate(LocalDate.now());  // You can use the travelDate if necessary
//
//        // Check if there are enough available seats
//        if (availableSeats >= bookingRequest.getNumberOfSeats()) {
//            booking.setStatus(BookingStatus.CONFIRMED);
//        } else {
//            booking.setStatus(BookingStatus.WAITING);
//        }
//
//        // Save the booking and return the updated booking object
//        return bookingRepository.save(booking);
//    }
    public Booking processBooking(BookingRequest bookingRequest) {
    // Fetch the train details using the trainId
    Train train = trainClient.getTrainById(bookingRequest.getTrainId());
//    if (train == null) {
//        throw new TrainNotFoundException("Train not found with ID: " + bookingRequest.getTrainId());
//    }

    // Get the number of already booked seats for the train
    int alreadyBookedSeats = bookingRepository.getTotalBookedSeatsByTrainId(bookingRequest.getTrainId());
    int availableSeats = train.getTotalSeats() - alreadyBookedSeats;

    // Create a new booking entity
    Booking booking = new Booking();
    booking.setTrainId(bookingRequest.getTrainId());
    booking.setUserId(bookingRequest.getUserId());
    booking.setNumberOfSeats(bookingRequest.getNumberOfSeats());
    booking.setBookingDate(LocalDate.now());  // You can use the travelDate if necessary

    // Logic to check if the booking can be confirmed, cancelled, or waiting
    if (availableSeats >= bookingRequest.getNumberOfSeats()) {
        // If enough seats are available, confirm the booking
        booking.setStatus(BookingStatus.CONFIRMED);
    } else if (availableSeats == 0) {
        // If no seats are available, cancel the booking
        booking.setStatus(BookingStatus.CANCELLED);
    } else {
        // If there are some seats available but not enough for the full request, set the status to WAITING
        booking.setStatus(BookingStatus.WAITING);
    }

    // Save the booking and return the updated booking object
    return bookingRepository.save(booking);
}

}
