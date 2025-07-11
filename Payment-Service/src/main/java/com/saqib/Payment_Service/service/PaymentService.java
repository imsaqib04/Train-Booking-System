package com.saqib.Payment_Service.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.saqib.Payment_Service.dto.*;
import com.saqib.Payment_Service.enums.PaymentStatus1;
import com.saqib.Payment_Service.feign.BookingClient;
import com.saqib.Payment_Service.model.Payment;
import com.saqib.Payment_Service.repo.PaymentRepository;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PaymentService {

    /* ------------------------------------------------------------
     * logger
     * ------------------------------------------------------------ */
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    /* ------------------------------------------------------------
     * injected beans
     * ------------------------------------------------------------ */
    @Autowired private RazorpayClient    razorpayClient;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private BookingClient     bookingClient;     // internal Feign

    /* ------------------------------------------------------------
     * config values
     * ------------------------------------------------------------ */
    @Value("${razorpay.key_id}")                   private String  keyId;
    @Value("${razorpay.key_secret}")               private String  keySecret;
    @Value("${razorpay.payment_capture}")          private int     paymentCapture; // 1 = auto‑capture
    @Value("${razorpay.webhook-secret}")           private String  webhookSecret;
    @Value("${app.skip-signature-check:false}")    private boolean skipSigCheck;

    /* ============================================================
     * 1) CREATE ORDER – returns data required by Razorpay Checkout
     * ============================================================ */
    @Transactional
    public PaymentResponseDto createOrder(PaymentRequestDto dto) throws RazorpayException {

        /* 1‒A get fare from Booking‑Service */
        System.out.println ("before");
        BookingDto booking = dto.hasBookingId()
                ? bookingClient.getBookingById(dto.getBookingId())
                : bookingClient.getBookingByPnr(dto.getPnr());

        System.out.println ("after");
        BigDecimal fare     = booking.getTotalFare();
        int amountInPaise   = fare.multiply(BigDecimal.valueOf(100)).intValueExact();

        /* 1‒B create Razorpay order */
        Order order = razorpayClient.orders.create(new JSONObject()
                .put("amount", amountInPaise)
                .put("currency", dto.getCurrency())
                .put("receipt", booking.getBookingId() != null
                        ? booking.getBookingId().toString()
                        : booking.getPnrNumber())
                .put("payment_capture", paymentCapture));

        /* 1‒C persist our Payment row */
        Payment pay = new Payment();
        pay.setBookingId (booking.getBookingId());
        pay.setPnrNumber (booking.getPnrNumber());
        pay.setOrderId   (order.get("id"));
        pay.setAmount    (fare);
        pay.setCurrency  (dto.getCurrency());
        pay.setStatus    (PaymentStatus1.CREATED);
        pay.setCreatedAt (LocalDateTime.now());
        paymentRepository.save(pay);

        /* 1‒D response for frontend */
        PaymentResponseDto res = new PaymentResponseDto();
        res.setOrderId   (order.get("id"));
        res.setBookingId (booking.getBookingId());
        res.setPnr       (booking.getPnrNumber());
        res.setAmount    (fare.intValueExact());
        res.setCurrency  (dto.getCurrency());
        res.setRazorpayKey(keyId);
        return res;
    }

    /* ============================================================
     * 2) VERIFY & CAPTURE – called by UI right after checkout
     * ============================================================ */
    @Transactional
    public void verifyAndCapture(PaymentVerificationDto dto) throws Exception {

        /* 2‒A signature verification */
        if (!skipSigCheck) {
            Utils.verifyPaymentSignature(new JSONObject(Map.of(
                    "razorpay_order_id", dto.getOrderId(),
                    "razorpay_payment_id", dto.getPaymentId(),
                    "razorpay_signature", dto.getSignature())), keySecret);
        } else {
            log.warn("⚠️  Signature check skipped (dev mode)");
        }

        /* 2‒B update DB */
        Payment pay = paymentRepository.findByOrderId(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown orderId " + dto.getOrderId()));

        pay.setPaymentId(dto.getPaymentId());
        pay.setStatus   ( PaymentStatus1.CAPTURED);
        pay.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(pay);

        /* 2‒C notify Booking‑Service so it can issue & email the ticket */
        PaymentInfoDto info = new PaymentInfoDto(dto.getPaymentId());


        log.info("🚀 Sending confirmPaid to Booking-Service → bookingId: {}, paymentId: {}", dto.getBookingId(), dto.getPaymentId());

        bookingClient.confirmPaid(dto.getBookingId(), new PaymentInfoDto(dto.getPaymentId()));

        if (dto.hasBookingId()) {
            bookingClient.confirmPaid(dto.getBookingId(), info);
            log.info("🔔 Booking #{} confirmed & ticket flow triggered", dto.getBookingId());
        } else if (dto.hasPnr()) {
            bookingClient.confirmPaidByPnr(dto.getPnr(), info);
            log.info("🔔 Booking PNR {} confirmed & ticket flow triggered", dto.getPnr());
        } else {
            throw new IllegalArgumentException("Either bookingId or PNR must be supplied");
        }

    }
//
    /* ============================================================
     * 3) RAZORPAY WEBHOOK – optional safety net
     * ============================================================ */
    @Transactional
    public void handleWebhook(String payload, String signatureHeader) throws Exception {

        if (!Utils.verifyWebhookSignature(payload, signatureHeader, webhookSecret)) {
            throw new IllegalArgumentException("Invalid webhook signature");
        }

        JSONObject entity = new JSONObject(payload)
                .getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String orderId   = entity.getString("order_id");
        String paymentId = entity.getString("id");
        String status    = entity.getString("status"); // captured / failed / refunded …

        paymentRepository.findByOrderId(orderId).ifPresent(p -> {
            p.setPaymentId(paymentId);
            p.setStatus   (PaymentStatus1.valueOf(status.toUpperCase()));
            p.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(p);

            /* Optional: if captured came via webhook before UI callback */
            if (p.getStatus() == PaymentStatus1.CAPTURED) {
                PaymentInfoDto info = new PaymentInfoDto(paymentId);
                if (p.getBookingId() != null) {
                    bookingClient.confirmPaid(p.getBookingId(), info);
                } else {
                    bookingClient.confirmPaidByPnr(p.getPnrNumber(), info);
                }
            }
        });
    }
}
