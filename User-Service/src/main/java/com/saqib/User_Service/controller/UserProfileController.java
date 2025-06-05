package com.saqib.User_Service.controller;

import com.saqib.User_Service.model.UserProfile;
import com.saqib.User_Service.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserProfileService service;


    @PostMapping("/save")
    public ResponseEntity<UserProfile> save(@RequestBody UserProfile profile) {
        return ResponseEntity.ok(service.saveProfile(profile));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> get(@PathVariable String userId) {
        return ResponseEntity.ok(service.getProfileByUserId(userId));
    }
//
    @GetMapping("/email/{email}")
    public ResponseEntity<UserProfile> getUserByEmail(@PathVariable String email) {
        UserProfile user = service.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Update Profile
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable String userId, @RequestBody UserProfile updatedProfile) {
        return ResponseEntity.ok(service.updateProfile(userId, updatedProfile));
    }

    // ✅ Delete Profile
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable String userId) {
        service.deleteProfile(userId);
        return ResponseEntity.ok("User profile deleted successfully");
    }

}
