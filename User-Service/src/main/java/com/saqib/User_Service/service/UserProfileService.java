package com.saqib.User_Service.service;


import com.saqib.User_Service.dto.ResponseDtoForEmail;
import com.saqib.User_Service.dto.UserProfileRequestDto;

import com.saqib.User_Service.model.User;
import com.saqib.User_Service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;
//
//    public void saveOrUpdateProfile(UserProfileRequestDto dto) {
//        User profile = userRepository.findByEmail(dto.getEmail()).orElse(new User());
//
//        // ✅ Set email if new user (because new User() has null email)
//        if (profile.getEmail() == null) {
//            profile.setEmail(dto.getEmail());
//        }
//
//        // ✅ Prevent profile overwrite
//        if (profile.getFullName() != null) {
//            throw new RuntimeException("⚠️ Profile already completed");
//        }
//
//        profile.setFullName(dto.getName());
//        profile.setPhoneNumber(dto.getPhoneNumber());
//        profile.setGender(dto.getGender());
//        profile.setDateOfBirth(dto.getDateOfBirth());
//        profile.setAddress(dto.getAddress());
//        profile.setCity(dto.getCity());
//        profile.setState(dto.getState());
//        profile.setPinCode(dto.getPinCode());
//        profile.setAadhaarNumber(dto.getAadhaarNumber());
//        profile.setPanNumber(dto.getPanNumber());
//        profile.setProfileImageUrl(dto.getProfileImageUrl());
//
//        userRepository.save(profile);
//    }

    public void saveOrUpdateProfile(UserProfileRequestDto dto) {
        // Find user by email
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

        // ❌ If email is not present in DB, reject the request
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("❌ Email not found in User Service DB. Please login first.");
        }

        User profile = optionalUser.get();

        // ✅ Prevent profile overwrite
        if (profile.getFullName() != null) {
            throw new RuntimeException("⚠️ Profile already completed");
        }

        // ✅ Now update profile fields
        profile.setFullName(dto.getUsername ());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setGender(dto.getGender());
        profile.setDateOfBirth(dto.getDateOfBirth());
        profile.setAddress(dto.getAddress());
        profile.setCity(dto.getCity());
        profile.setState(dto.getState());
        profile.setPinCode(dto.getPinCode());
        profile.setAadhaarNumber(dto.getAadhaarNumber());
        profile.setPanNumber(dto.getPanNumber());
        profile.setProfileImageUrl(dto.getProfileImageUrl());

        // ✅ Save profile
        userRepository.save(profile);
    }


//    public void saveOrUpdateProfile(UserProfileRequestDto dto, String emailFromToken) {
//        User user = userRepository.findByEmail(emailFromToken)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (user.getFullName() != null) {
//            throw new RuntimeException("⚠️ Profile already completed");
//        }
//
//
//        user.setFullName(dto.getName());
//        user.setPhoneNumber(dto.getPhoneNumber());
//        user.setGender(dto.getGender());
//        user.setDateOfBirth(dto.getDateOfBirth());
//        user.setAddress(dto.getAddress());
//        user.setCity(dto.getCity());
//        user.setState(dto.getState());
//        user.setPinCode(dto.getPinCode());
//        user.setAadhaarNumber(dto.getAadhaarNumber());
//        user.setPanNumber(dto.getPanNumber());
//        user.setProfileImageUrl(dto.getProfileImageUrl());
//
//        userRepository.save(user);
//    }


    public void deleteProfileByEmail(String email) {
        userRepository.deleteByEmail(email);
    }


//    public void save(ResponseDtoForEmail responseDtoForEmail) {
//        Optional<User> existingUser = userRepository.findByEmail(responseDtoForEmail.getEmail());
//
//        if (existingUser.isEmpty()) {
//            User user = new User();
//            user.setEmail(responseDtoForEmail.getEmail());
//            userRepository.save(user);
//        }
//    }

//    public ResponseEntity<String> saveUser(ResponseDtoForEmail dto) {
//        if (!userRepository.existsByEmail(dto.getEmail())) {
//            User user = new User();
//            user.setEmail(dto.getEmail());
//            userRepository.save(user);
//            return ResponseEntity.ok("User saved");
//        }
//        return ResponseEntity.ok("User already exists");
//    }

//    public ResponseEntity<String> saveUser(ResponseDtoForEmail dto) {
//        Optional<User> existing = userRepository.findByEmail(dto.getEmail());
//
//        if (existing.isPresent()) {
//            System.out.println("⚠️ User already exists in User Service: " + dto.getEmail());
//            return ResponseEntity.ok("User already exists");
//        }
//
//        User user = new User();
//        user.setEmail(dto.getEmail());
//        userRepository.save(user);
//        System.out.println("✅ User saved in User Service: " + dto.getEmail());
//        return ResponseEntity.ok("User saved");
//    }

    public ResponseEntity<String> saveUser(ResponseDtoForEmail dto) {
        Optional<User> existing = userRepository.findByEmail(dto.getEmail());

        if (existing.isPresent()) {
            return ResponseEntity.status( HttpStatus.CONFLICT)
                    .body("⚠️ Email already exists in User Service.");
        }

        User newUser = new User();
        newUser.setEmail(dto.getEmail());

        userRepository.save(newUser);

        return ResponseEntity.ok("✅ Email saved successfully in User Service.");
    }

}
