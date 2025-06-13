package com.saqib.Auth_Service.controller;

import com.saqib.Auth_Service.dto.*;
import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.repo.UserRepository;
import com.saqib.Auth_Service.service.AuthService;
import com.saqib.Auth_Service.service.EmailService;
import com.saqib.Auth_Service.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            authService.register ( user );
            return ResponseEntity.ok ( "Registration successful. Please check your email to verify." );
        } catch (Exception e) {
            return ResponseEntity.badRequest ().body ( "Error: " + e.getMessage () );
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login ( loginRequest );
            return ResponseEntity.ok ( response );
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status ( 401 ).body ( Map.of ( "error", ex.getMessage () ) );
        } catch (Throwable ex) {
            return ResponseEntity.status ( 400 ).body ( Map.of ( "error", ex.getMessage () ) );
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<OAuth2UserProfileDTO> getProfile(Principal principal) {
        OAuth2UserProfileDTO profile = authService.getProfile ( principal );
        return ResponseEntity.ok ( profile );
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {
        String result = String.valueOf ( authService.verifyEmailToken ( token ) );
        if ("success".equals ( result )) {
            return ResponseEntity.ok ( "Email verified. You can now log in." );
        }
        return ResponseEntity.badRequest ().body ( result );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) throws Throwable {
        authService.forgotPassword ( request.getEmail () );
        return ResponseEntity.ok ( "Reset link sent to email" );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws Throwable {
        authService.resetPassword ( request.getToken (), request.getNewPassword () );
        return ResponseEntity.ok ( "Password reset successful" );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout ( authHeader );
        return ResponseEntity.ok ( "✅ Logged out successfully. Token blacklisted." );
    }

    // here we add otp fun:

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) throws Throwable {
        return otpService.sendOtpToEmail(email);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody ResetPasswordDto resetPasswordDto) throws Throwable {
        return otpService.verifyOtpAndResetPassword(resetPasswordDto);
    }

}