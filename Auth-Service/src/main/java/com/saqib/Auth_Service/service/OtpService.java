package com.saqib.Auth_Service.service;

import com.saqib.Auth_Service.dto.ResetPasswordDto;
import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


    @Service
    public class OtpService {

        @Autowired
        private InMemoryOtpStorage inMemoryOtpStorage;

        @Autowired
        private EmailService emailService;

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

//        public String generateOtp(String email) {
//            String otp = String.valueOf ( (int) (Math.random () * 900000) + 100000 ); // 6-digit OTP
//            inMemoryOtpStorage.storeOtp ( email, otp );
//            return otp;
//        }
//
//        public boolean validateOtp(String email, String otp) {
//            return inMemoryOtpStorage.validateOtp ( email, otp );
//        }
//
//        public void clearOtp(String email) {
//            inMemoryOtpStorage.clearOtp ( email );
//        }
//
//        public ResponseEntity<?> sendOtpToEmail(String email) throws Throwable {
//            User user = (User) userRepo.findByEmail ( email )
//                    .orElseThrow ( () -> new RuntimeException ( "Email not found" ) );
//
//            String otp = generateOtp ( email );
//
//            emailService.sendEmail (
//                    email,
//                    "Your OTP Code",
//                    "Your OTP is: " + otp + " (valid for 5 minutes)"
//            );
//
//            return ResponseEntity.ok ( "OTP sent to email." );
//        }
//
//        public ResponseEntity<?> verifyOtpAndResetPassword(String email, String otp, String newPassword) throws Throwable {
//            if (!validateOtp ( email, otp )) {
//                return ResponseEntity.status ( 400 ).body ( "Invalid or expired OTP" );
//            }
//
//            User user = (User) userRepo.findByEmail ( email )
//                    .orElseThrow ( () -> new RuntimeException ( "User not found" ) );
//
//            user.setPassword ( passwordEncoder.encode ( newPassword ) );
//            userRepo.save ( user );
//
//            clearOtp ( email );
//
//            return ResponseEntity.ok ( "Password reset successful." );
//        }
//    }

        public String generateOtp(String email) {
            String otp = String.valueOf ( (int) (Math.random () * 900000) + 100000 ); // 6-digit OTP
            inMemoryOtpStorage.storeOtp ( email, otp );
            return otp;
        }

        public boolean validateOtp(String email, String otp) {
            return inMemoryOtpStorage.validateOtp ( email, otp );
        }

        public void clearOtp(String email) {
            inMemoryOtpStorage.clearOtp ( email );
        }

        public ResponseEntity<?> sendOtpToEmail(String email) throws Throwable {
            User user = (User) userRepo.findByEmail ( email )
                    .orElseThrow ( () -> new RuntimeException ( "Email not found" ) );

            String otp = generateOtp ( email );

            emailService.sendEmail (
                    email,
                    "Your OTP Code",
                    "Your OTP is: " + otp + " (valid for 5 minutes)"
            );

            return ResponseEntity.ok ( "OTP sent to email." );
        }

        public ResponseEntity<?> verifyOtpAndResetPassword(ResetPasswordDto dto) throws Throwable {
            String email = dto.getEmail ();
            String otp = dto.getOtp ();
            String newPassword = dto.getNewPassword ();

            if (!validateOtp ( email, otp )) {
                return ResponseEntity.badRequest ().body ( "Invalid or expired OTP" );
            }

            User user = (User) userRepo.findByEmail ( email )
                    .orElseThrow ( () -> new RuntimeException ( "User not found" ) );

            user.setPassword ( passwordEncoder.encode ( newPassword ) );
            userRepo.save ( user );
            clearOtp ( email );

            return ResponseEntity.ok ( "Password reset successful." );
        }
    }
