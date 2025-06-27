package com.saqib.Payment_Service.controller;

import com.saqib.Payment_Service.dto.PaymentRequestDto;
import com.saqib.Payment_Service.dto.PaymentResponseDto;
import com.saqib.Payment_Service.dto.PaymentVerificationDto;
import com.saqib.Payment_Service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = {"http://localhost:5500","http://127.0.0.1:5500"})
public class PaymentController {

    @Autowired
    private PaymentService service;

    /** Step 1 – create Razorpay order */
    @PostMapping("/order")
    public ResponseEntity<PaymentResponseDto> createOrder(
            @RequestBody PaymentRequestDto dto) throws Exception {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOrder(@RequestBody PaymentVerificationDto dto) {
        try {
            service.verifyAndCapture(dto);
            return ResponseEntity.ok("Payment verified & captured");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Verification failed: " + ex.getMessage());
        }
    }

    /*  Razorpay will POST every event here  */
    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(
            HttpServletRequest request,
            @RequestHeader("X-Razorpay-Signature") String signatureHeader) {

        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader (
                    new InputStreamReader (request.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String payload = sb.toString();

            service.handleWebhook(payload, signatureHeader);
            return ResponseEntity.ok("received");
        } catch (Exception ex) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST)
                    .body("webhook-error");
        }
    }
}
