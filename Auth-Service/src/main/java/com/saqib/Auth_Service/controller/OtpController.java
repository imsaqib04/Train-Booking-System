//package com.saqib.Auth_Service.controller;
//
//import com.saqib.Auth_Service.model.User;
//import com.saqib.Auth_Service.repo.UserRepository;
//import com.saqib.Auth_Service.service.EmailService;
//import com.saqib.Auth_Service.service.OtpService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//public class OtpController {
//
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private OtpService otpService;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @PostMapping("/send-otp")
//    public ResponseEntity<?> sendOtp(@RequestParam String email) throws Throwable {
//        User user = (User) userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Email not found"));
//
//        String otp = otpService.generateOtp(email);
//
//        emailService.sendEmail(
//                email,
//                "Your OTP Code",
//                "Your OTP is: " + otp + " (valid for a few minutes)"
//        );
//
//        return ResponseEntity.ok("OTP sent to email.");
//    }
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(@RequestParam String email,
//                                       @RequestParam String otp,
//                                       @RequestParam String newPassword) throws Throwable {
//        if (!otpService.validateOtp(email, otp)) {
//            return ResponseEntity.status(400).body("Invalid or expired OTP");
//        }
//
//        User user = (User) userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepo.save(user);
//        otpService.clearOtp(email);
//
//        return ResponseEntity.ok("Password reset successful.");
//    }
//
//
//}
