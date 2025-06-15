package com.saqib.Auth_Service.service;

import com.saqib.Auth_Service.dto.*;
import com.saqib.Auth_Service.feign.UserServiceClient;
import com.saqib.Auth_Service.model.BlacklistedToken;
import com.saqib.Auth_Service.model.Role;
import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.repo.BlacklistedTokenRepository;
import com.saqib.Auth_Service.repo.UserRepository;
import com.saqib.Auth_Service.utill.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.SQLOutput;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceClient userServiceClient;

//    public void register(User user) {
//        if (userRepo.findByEmail ( user.getEmail () ).isPresent ()) {
//            throw new RuntimeException ( "Email already exists" );
//        }
//
//        user.setPassword ( passwordEncoder.encode ( user.getPassword () ) );
//        user.setEnabled ( false );
//        userRepo.save ( user );
//
//        String token = jwtUtil.generateEmailVerificationToken ( user.getEmail () );
//
//        String link = "http://localhost:8091/api/auth/verify?token=" + token;
//
//        try {
//            emailService.sendEmail (
//                    user.getEmail (),
//                    "Verify your email",
//                    "Click here to verify your account: " + link
//            );
//        } catch (Exception e) {
//            throw new RuntimeException ( "Email sending failed: " + e.getMessage () );
//        }
//    }

    public ResponseEntity<?> register(SignUpRequest request) {
        // 1. Check if user already exists
        System.out.println ("Step-2");
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 2. Save user in Auth DB
        User user = new User();
        user.setUsername ( request.getUsername () );
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false); // wait for verification
        user.setEmailVerified(false);
//        user.setRole( Role.valueOf(request.getRole().toUpperCase()));
        // ✅ Safer enum parsing
        try {
            user.setRole(Role.fromString(request.getRole()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Invalid role: must be 'user' or 'admin'");
        }

        user.setProvider("LOCAL");

        userRepo.save(user);

        // 4. Generate email verification token
        String token = jwtUtil.generateEmailVerificationToken(request.getEmail());
        String link = "http://localhost:8091/api/auth/verify?token=" + token;

        try {
            emailService.sendEmail(
                    request.getEmail(),
                    "Verify your email",
                    "Click here to verify your account: " + link
            );
        } catch (Exception e) {
            throw new RuntimeException("❌ Email sending failed: " + e.getMessage());
        }

        System.out.println ("Step-6");

        // 5. Return success response (optionally with token)
        return ResponseEntity.ok("✅ Registration successful! Please verify your email.");
    }


//    public LoginResponse login(LoginRequest request) throws Throwable {
//        User user = (User) userRepo.findByEmail ( request.getEmail () )
//                .orElseThrow ( () -> new UsernameNotFoundException ( "User not found" ) );
//
//        if (!passwordEncoder.matches ( request.getPassword (), user.getPassword () )) {
//            throw new BadCredentialsException ( "Invalid password" );
//        }
//
//        if (!user.isEnabled ()) {
//            throw new RuntimeException ( "Please verify your email before login" );
//        }
//
//        String token = jwtUtil.generateToken ( user.getEmail () );
//        return new LoginResponse ( token, "Login successful" );
//    }

//    public LoginResponse login(LoginRequest request) throws Throwable {
//        User user = (User) userRepo.findByEmail(request.getEmail())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        if (!user.isEnabled()) {
//            throw new RuntimeException("Please verify your email before login");
//        }
//
//        String token = jwtUtil.generateToken(user.getEmail());
//        return new LoginResponse(token, "Login successful");
//    }

    public LoginResponse login(LoginRequest request) throws Throwable {
        // Step 1: Find user by email
        User user = (User) userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Step 2: Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Step 3: Check email verification
        if (!user.isEnabled()) {
            throw new RuntimeException("Please verify your email before login");
        }

        // Step 4: Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // Step 5: Send verified email to User Service (if not already saved)
        ResponseDtoForEmail responseDtoForEmail = new ResponseDtoForEmail();
        responseDtoForEmail.setEmail(user.getEmail());

        System.out.println ("before saving user service");
        System.out.println("📤 Sending verified user email to User Service...");
        try {
            userServiceClient.saveUser(responseDtoForEmail, "Bearer " + token);
            System.out.println("✅ Email saved in User Service.");
        } catch (Exception e) {
            System.out.println("⚠️ User not saved in User Service: " + e.getMessage());
        }
        System.out.println ("after saving user service");

        // Step 6: Return token response
        return new LoginResponse(token, "Login successful");
    }




    public OAuth2UserProfileDTO getProfile(Principal principal) {
        System.out.println ( "hello saqib" );
        if (principal == null) {
            throw new RuntimeException ( "User not authenticated" );
        }

        System.out.println ( "ok done this " );

        // email is set as principal name
        String email = principal.getName ();

        Optional<User> optionalUser = userRepo.findByEmail ( email );
        if (optionalUser.isEmpty ()) {
            throw new RuntimeException ( "User not found with email: " + email );
        }

        User user = optionalUser.get ();
        return new OAuth2UserProfileDTO ( user.getUsername (), user.getEmail (), user.getProvider () );
    }


    public ResponseEntity<String> resendVerification(String email) {
        Optional<User> optionalUser = userRepo.findByEmail ( email );
        if (optionalUser.isEmpty ()) return ResponseEntity.badRequest ().body ( "Email not registered." );

        User user = optionalUser.get ();
        if (user.isEnabled ()) return ResponseEntity.ok ( "Email already verified." );

        String token = jwtUtil.generateEmailVerificationToken ( email );
        String verifyLink = "http://localhost:8091/api/auth/verify?token=" + token;

        emailService.sendEmail (
                email,
                "Resend Verification",
                "Click to verify your account: " + verifyLink
        );

        return ResponseEntity.ok ( "Verification link sent again to your email." );
    }

    public ResponseEntity<String> verifyEmailToken(String token) {
        String email;

        try {
            email = jwtUtil.extractUsername ( token );
        } catch (Exception e) {
            return ResponseEntity.badRequest ().body ( "Invalid or expired token." );
        }

        Optional<User> optionalUser = userRepo.findByEmail ( email );
        if (optionalUser.isEmpty ()) return ResponseEntity.badRequest ().body ( "User not found." );

        User user = optionalUser.get ();
        if (user.isEnabled ()) return ResponseEntity.ok ( "Email already verified." );

        user.setEmailVerified ( true );
        user.setEnabled ( true );
        userRepo.save ( user );

        return ResponseEntity.ok ( "Email verified successfully. You can now log in." );
    }

    public void forgotPassword(String email) throws Throwable {
        User user = (User) userRepo.findByEmail ( email )
                .orElseThrow ( () -> new RuntimeException ( "Email not registered" ) );

        String token = jwtUtil.generateEmailVerificationToken ( email ); // Reusing 15-min token

        String resetLink = "http://localhost:8091/api/auth/reset-password?token=" + token;
        emailService.sendEmail ( email, "Password Reset",
                "Click the link to reset your password: " + resetLink );  // yha se gmail pr ak link jayegi bha token ke aage bala hi hamara token hai usko copy krke reset password krna hai!
    }

    public void resetPassword(String token, String newPassword) throws Throwable {
        String email = jwtUtil.validateTokenAndGetUsername ( token );

        User user = (User) userRepo.findByEmail ( email )
                .orElseThrow ( () -> new RuntimeException ( "Invalid token or user" ) );

        user.setPassword ( passwordEncoder.encode ( newPassword ) );
        userRepo.save ( user );
        blacklistedTokenRepository.save (
                new BlacklistedToken ( token, jwtUtil.extractExpiration ( token ) )
        );
    }

    public void logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith ( "Bearer " )) {
            String token = authHeader.substring ( 7 );
            if (jwtUtil.isTokenValid ( token )) {
                blacklistedTokenRepository.save (
                        new BlacklistedToken ( token, jwtUtil.extractExpiration ( token ) )
                );
            }
        }
    }
}
