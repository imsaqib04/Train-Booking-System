package com.saqib.Auth_Service.service;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryOtpStorage {

    private final Map<String, String> otpMap = new ConcurrentHashMap<>();

    public void storeOtp(String email, String otp) {
        otpMap.put(email, otp);
    }

    public boolean validateOtp(String email, String otp) {
        return otp.equals(otpMap.get(email));
    }

    public void clearOtp(String email) {
        otpMap.remove(email);
    }
}

