package com.saqib.Payment_Service.model;

import com.saqib.Payment_Service.enums.PaymentStatus;
import com.saqib.Payment_Service.enums.PaymentStatus1;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;


    private String pnrNumber;

    @Column(nullable = false, unique = true)
    private String orderId;

    private String paymentId;          // comes after success
    private String receipt;            // your own identifier

    @Column(nullable = false)
    private BigDecimal amount;         // store in rupees

    @Column(length = 3, nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus1 status = PaymentStatus1.CREATED;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    // getters & setters (or Lombok @Data if you like)


    public Payment(Long id, Long bookingId, String pnrNumber, String orderId, String paymentId, String receipt, BigDecimal amount, String currency, PaymentStatus1 status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.pnrNumber = pnrNumber;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.receipt = receipt;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Payment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentStatus1 getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus1 status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }
}
