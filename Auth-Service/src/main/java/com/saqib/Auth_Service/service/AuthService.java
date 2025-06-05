package com.saqib.Auth_Service.service;

import com.saqib.Auth_Service.dto.UserProfile;
import com.saqib.Auth_Service.feign.UserServiceClient;
import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.repo.UserRepository;
import com.saqib.Auth_Service.utill.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<String> register(User user) {
        if (user.getEmail () == null || user.getEmail ().isEmpty ()) {
            return ResponseEntity.badRequest ().body ( "Email is required" );
        }

        // Check if email already exists
        Optional<User> existingUser = Optional.ofNullable ( userRepo.findByEmail ( user.getEmail () ) );
        if (existingUser.isPresent ()) {
            return ResponseEntity.status ( HttpStatus.CONFLICT )
                    .body ( "Email already registered" );
        }

        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save user
        userRepo.save ( user );

        // Create UserProfile
        UserProfile profile = new UserProfile ();
        profile.setUserId ( user.getUserId () );
        profile.setName ( user.getUserName () );
        profile.setEmail ( user.getEmail () );
        profile.setContactNumber ( user.getContactNumber () );
        profile.setAddress ( user.getAddress () );
        profile.setGender ( user.getGender () );
        profile.setDob ( user.getDob () );
        profile.setAadhaar ( user.getAadhaar () );
        profile.setRole ( user.getRole () );
        // profile.setPassword(user.getPassword());

        userServiceClient.saveUser ( profile );

        return ResponseEntity.ok ( "User registered successfully" );
    }


    public String login(String email, String password) {
        // Fetch user from Auth DB
        User user = userRepo.findByEmail ( email );

        // Validate credentials
        if (user == null || !passwordEncoder.matches ( password, user.getPassword () )) {
            throw new RuntimeException ( "Invalid email or password" );
        }

        // Generate JWT token
        return jwtUtil.generateToken ( user.getEmail () );
    }

}
