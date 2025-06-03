package com.saqib.Booking_Service.service;

import com.saqib.Booking_Service.BookingStatus;
import com.saqib.Booking_Service.dto.BookingRequest;
import com.saqib.Booking_Service.feign.TrainClient;
import com.saqib.Booking_Service.model.Booking;
import com.saqib.Booking_Service.repo.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TrainClient trainClient;

    public BookingService(BookingRepository bookingRepository, TrainClient trainClient) {
        this.bookingRepository = bookingRepository;
        this.trainClient = trainClient;
    }


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    public Booking createBooking(BookingRequest request) {
        Booking booking = new Booking();

        booking.setTrainId(request.getTrainId());
//        booking.setPassengerName("User_" + request.getUserId()); // Example
        booking.setPassengerName ( request.getPassengerName () );
        booking.setSeatsBooked(request.getSeatsBooked ());
        booking.setTravelDate( LocalDate.parse(request.getTravelDate()));
        booking.setBookingDate(LocalDateTime.now());

        // Calculate fare
        double farePerSeat = 100.0;
        double totalFare = request.getSeatsBooked () * farePerSeat;
        booking.setTotalFare(totalFare);

        int totalSeats = trainClient.getTotalSeats(booking.getTrainId());
        int confirmedSeats = bookingRepository.countByTrainIdAndStatus(booking.getTrainId(), BookingStatus.CONFIRMED);

        if (confirmedSeats + booking.getSeatsBooked() > totalSeats) {
            booking.setStatus(BookingStatus.WAITING);
            booking.setWaitingListPosition(calculateWaitingListPosition(booking.getTrainId()));
        } else {
            booking.setStatus(BookingStatus.CONFIRMED);
            trainClient.updateAvailableSeats(booking.getTrainId(), booking.getSeatsBooked());
        }

        return bookingRepository.save(booking);
    }



//    // Cancel booking and update status + cancelDate
//    public String cancelBooking(Long bookingId) {
//        Optional<Booking> optBooking = bookingRepository.findById(bookingId);
//        if (optBooking.isEmpty()) {
//            return "Booking cancellation failed: Booking not found.";
//        }
//
//        Booking booking = optBooking.get();
//        if (booking.getStatus().equals(BookingStatus.CANCELLED.name())) {
//            return "Booking is already cancelled.";
//        }
//
//        booking.setStatus( BookingStatus.valueOf ( String.valueOf ( BookingStatus.CANCELLED ) ) );
//        booking.setCancelDate(LocalDateTime.now());
//        bookingRepository.save(booking);
//        promoteWaitingList(booking.getTrainId());
//
//        // Optional: handle waiting list promotion here (not implemented now)
//
//        return "Booking cancelled successfully.";
//    }


    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            // Step 1: Increase available seats in train service
            trainClient.increaseAvailableSeats(booking.getTrainId(), booking.getSeatsBooked());

            // Step 2: Update booking status
            booking.setStatus(BookingStatus.CANCELLED);
            booking.setCancelDate(LocalDateTime.now());

            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Only CONFIRMED bookings can be cancelled");
        }
    }


    private Integer calculateWaitingListPosition(Long trainId) {
        Integer maxPosition = bookingRepository.findMaxWaitingListPositionByTrainId(trainId);
        if (maxPosition == null) {
            return 1;
        }
        return maxPosition + 1;
    }
    private void promoteWaitingList(Long trainId) {
        int totalSeats = trainClient.getTotalSeats(trainId);
        int confirmedSeats = bookingRepository.countByTrainIdAndStatus(trainId, BookingStatus.CONFIRMED);

        // How many seats free now?
        int freeSeats = totalSeats - confirmedSeats;

        if (freeSeats <= 0) return; // no free seats to promote

        // Get waiting list bookings ordered by waitingListPosition ascending
        List<Booking> waitingList = bookingRepository.findByTrainIdAndStatusOrderByWaitingListPositionAsc(trainId, BookingStatus.WAITING);

        for (Booking waitingBooking : waitingList) {
            if (waitingBooking.getSeatsBooked() <= freeSeats) {
                // Promote this booking
                waitingBooking.setStatus( BookingStatus.valueOf ( String.valueOf ( BookingStatus.CONFIRMED ) ) );
                waitingBooking.setWaitingListPosition(null);
                waitingListPositionShift(waitingBooking); // optional to reorder waiting list after promotion
                bookingRepository.save(waitingBooking);

                freeSeats -= waitingBooking.getSeatsBooked();
            } else {
                break; // not enough seats for next waiting booking
            }
        }
    }
    private void waitingListPositionShift(Booking promotedBooking) {
        Long trainId = promotedBooking.getTrainId();
        Integer promotedPosition = promotedBooking.getWaitingListPosition();

        if (promotedPosition == null) return; // already removed from waiting list

        // Find all waiting bookings with position > promotedPosition
        List<Booking> waitingListToShift = bookingRepository
                .findByTrainIdAndStatusAndWaitingListPositionGreaterThanOrderByWaitingListPositionAsc(
                        trainId, BookingStatus.WAITING, promotedPosition);

        // Decrement waiting list position by 1 for all these bookings
        for (Booking booking : waitingListToShift) {
            booking.setWaitingListPosition(booking.getWaitingListPosition() - 1);
        }

        bookingRepository.saveAll(waitingListToShift);
    }
}
