package com.saqib.User_Service.service;


import com.saqib.User_Service.dto.UserSyncDto;
import com.saqib.User_Service.dto.UserProfileRequestDto;

import com.saqib.User_Service.model.Role;
import com.saqib.User_Service.model.User;
import com.saqib.User_Service.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

//    public void saveOrUpdateProfile(UserProfileRequestDto dto) {
//        // Find user by email
//        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
//
//        // ❌ If email is not present in DB, reject the request
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("❌ Email not found in User Service DB. Please login first.");
//        }
//
//        User profile = optionalUser.get();
//
//        // ✅ Prevent profile overwrite
//        if (profile.getFullName() != null) {
//            throw new RuntimeException("⚠️ Profile already completed");
//        }
//
//        // ✅ Now update profile fields
//        profile.setFullName(dto.getFullName ());
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
//        // ✅ Save profile
//        userRepository.save(profile);
//    }


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

    public void saveOrUpdateProfile(String email, UserProfileRequestDto dto) {

        User profile = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("❌ Email not found. Please login first."));

        if (profile.getFullName() != null) {
            throw new RuntimeException("⚠️ Profile already completed");
        }

        profile.setFullName(dto.getFullName());
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

        userRepository.save(profile);
    }




    @Transactional
    public void deleteProfileByEmail(String email) {
        userRepository.deleteByEmail(email);
    }



    public ResponseEntity<String> saveUser(UserSyncDto dto) {
        Optional<User> existing = userRepository.findByEmail(dto.getEmail());

        if (existing.isPresent()) {
            return ResponseEntity.status( HttpStatus.CONFLICT)
                    .body("⚠️ Email already exists in User Service.");
        }

        User newUser = new User();
        newUser.setEmail(dto.getEmail());
        newUser.setUsername ( dto.getUsername () );
        newUser.setRole ( Role.valueOf ( dto.getRole () ) );

        userRepository.save(newUser);

        return ResponseEntity.ok("✅ Email saved successfully in User Service.");
    }

}
