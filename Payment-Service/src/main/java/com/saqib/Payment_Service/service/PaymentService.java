package com.saqib.Payment_Service.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.saqib.Payment_Service.dto.PaymentRequestDto;
import com.saqib.Payment_Service.dto.PaymentResponseDto;
import com.saqib.Payment_Service.dto.PaymentVerificationDto;
import com.saqib.Payment_Service.model.Payment;
import com.saqib.Payment_Service.model.PaymentStatus;
import com.saqib.Payment_Service.repo.PaymentRepository;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    /* ---------- Dependencies ---------- */
    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceService invoiceService;

    /* ---------- Config values ---------- */
    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @Value("${razorpay.payment_capture}")
    private int paymentCapture;              // 1 = auto-capture, 0 = authorise only

    @Value("${razorpay.webhook-secret}")
    private String webhookSecret;

    /* ====================================================================== */
    /* 1. CREATE ORDER                                                        */
    /* ====================================================================== */
    @Transactional
    public PaymentResponseDto createOrder(PaymentRequestDto dto) throws RazorpayException {

        int amountPaise = dto.getAmount() * 100;   // Razorpay paise mein leta hai

        JSONObject opts = new JSONObject();
        opts.put("amount",          amountPaise);
        opts.put("currency",        dto.getCurrency());
        opts.put("receipt",         dto.getReceipt());
        opts.put("payment_capture", paymentCapture);

        Order order = razorpayClient.orders.create(opts);

        /* -- DB save -- */
        Payment payment = new Payment();
        payment.setOrderId(order.get("id"));
        payment.setAmount(BigDecimal.valueOf(dto.getAmount()));
        payment.setCurrency(dto.getCurrency());
        payment.setReceipt(dto.getReceipt());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        /* -- Response for frontend / caller -- */
        PaymentResponseDto res = new PaymentResponseDto();
        res.setOrderId(order.get("id"));
        res.setAmount(dto.getAmount());
        res.setCurrency(dto.getCurrency());
        res.setRazorpayKey(keyId);

        return res;
    }

    /* ==================================================================== */
    /* 2. VERIFY SIGNATURE (client-side flow) & CAPTURE STATUS UPDATE       */
    /* ==================================================================== */

    @Transactional
    public void verifyAndCapture(PaymentVerificationDto dto) throws Exception {

        // ✅ Prepare and convert to JSONObject
        Map<String, String> attrs = new HashMap<>();
        attrs.put("razorpay_order_id", dto.getOrderId());
        attrs.put("razorpay_payment_id", dto.getPaymentId());
        attrs.put("razorpay_signature", dto.getSignature());

        JSONObject jsonAttrs = new JSONObject(attrs); // 👈 this is key fix
        Utils.verifyPaymentSignature(jsonAttrs, keySecret);

        // ✅ Update DB
        Payment payment = paymentRepository.findByOrderId(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        payment.setPaymentId(dto.getPaymentId());
        payment.setStatus(PaymentStatus.CAPTURED);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // ✅ Send invoice
        invoiceService.sendInvoice(payment);
    }


    /* ====================================================================== */
    /* 3. WEBHOOK HANDLER (server-side only flow)                             */
    /* ====================================================================== */
    @Transactional
    public void handleWebhook(String payload, String signatureHeader) throws Exception {

        boolean valid = Utils.verifyWebhookSignature(payload, signatureHeader, webhookSecret);
        if (!valid) {
            throw new IllegalArgumentException("Invalid webhook signature");
        }

        JSONObject paymentJson = new JSONObject(payload)
                .getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");

        String paymentId = paymentJson.getString("id");
        String orderId   = paymentJson.getString("order_id");
        String status    = paymentJson.getString("status");   // captured / failed / authorised …

        Optional<Payment> opt = paymentRepository.findByOrderId(orderId);
        if (opt.isPresent()) {
            Payment payment = opt.get();
            payment.setPaymentId(paymentId);
            payment.setStatus(PaymentStatus.valueOf(status.toUpperCase()));
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
        }
    }
}
