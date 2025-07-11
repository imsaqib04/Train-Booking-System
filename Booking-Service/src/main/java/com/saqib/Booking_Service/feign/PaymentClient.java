package com.saqib.Booking_Service.feign;

import com.saqib.Booking_Service.dto.PaymentRequestDto;
import com.saqib.Booking_Service.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8095")
public interface PaymentClient {

    @PostMapping("/api/payments/order")
    PaymentResponseDto createOrder(@RequestBody PaymentRequestDto dto);
}
