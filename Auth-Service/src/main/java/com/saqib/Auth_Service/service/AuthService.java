package com.saqib.Auth_Service.service;

import com.saqib.Auth_Service.dto.OAuth2UserProfileDTO;
import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.repo.UserRepository;
import com.saqib.Auth_Service.utill.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        // Check if email already exists
        Optional<User> existingUser = userRepo.findByEmail ( user.getEmail () );
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider("LOCAL");

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public String login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null; // will be handled in controller
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null; // also will be handled
        }

        return jwtUtil.generateToken(user.getEmail());
    }


    public OAuth2UserProfileDTO getProfile(Principal principal) {
        System.out.println ("hello saqib");
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        System.out.println ("ok done this ");

        // email is set as principal name
        String email = principal.getName();

        Optional<User> optionalUser = userRepo.findByEmail ( email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = optionalUser.get();
        return new OAuth2UserProfileDTO(user.getUsername (), user.getEmail(), user.getProvider());
    }
}
