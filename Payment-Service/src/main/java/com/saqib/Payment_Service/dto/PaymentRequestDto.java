package com.saqib.Payment_Service.dto;

public class PaymentRequestDto {
    private int amount;        // rupees
    private String currency;   // e.g. "INR"
    private String receipt;    // optional reference
    // getters & setters

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}