package com.saqib.Booking_Service.dto;


import com.saqib.Booking_Service.BookingStatus;

public class BookingStatusResponse {
        private BookingStatus status;

        // Constructor, getters, and setters
        public BookingStatusResponse(BookingStatus status) {
            this.status = status;
        }

        public BookingStatus getStatus() {
            return status;
        }

        public void setStatus(BookingStatus status) {
            this.status = status;
        }
    }
