package com.saqib.Payment_Service.feign;

import com.saqib.Payment_Service.dto.BookingDto;
import com.saqib.Payment_Service.dto.PaymentInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BOOKING-SERVICE", path = "/booking")
public interface BookingClient {

    // Confirm by ID (used in payment success callback)
    @PostMapping("/{id}/payment-confirmed")
    void confirmPaid(@PathVariable("id") Long id,
                     @RequestBody PaymentInfoDto info);

    // Confirm by PNR (optional alternative)
    @PostMapping("/pnr/{pnr}/payment-confirmed")
    void confirmPaidByPnr(@PathVariable("pnr") String pnr,
                          @RequestBody PaymentInfoDto info);

    // Fetch booking info
    @GetMapping("/id/{bookingId}")
    BookingDto getBookingById(@PathVariable("bookingId") Long bookingId);

    @GetMapping("/pnr/{pnr}")
    BookingDto getBookingByPnr(@PathVariable("pnr") String pnr);
}
