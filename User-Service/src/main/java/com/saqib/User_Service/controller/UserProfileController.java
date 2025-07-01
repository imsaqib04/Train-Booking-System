package com.saqib.User_Service.controller;


import com.saqib.User_Service.dto.UserRequestDto;
import com.saqib.User_Service.dto.UserResponseDto;
import com.saqib.User_Service.dto.UserSyncDto;
import com.saqib.User_Service.dto.UserProfileRequestDto;
import com.saqib.User_Service.model.User;
import com.saqib.User_Service.service.UserProfileService;
import com.saqib.User_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private UserService userService;

    // create profile
    @PostMapping("/create")
    public ResponseEntity<String> createOrUpdateProfile(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserProfileRequestDto dto) {

        // "sub" claim me email already hai
        String email = jwt.getSubject();      // ya jwt.getClaim("email")

        profileService.saveOrUpdateProfile(email, dto);
        return ResponseEntity.ok("Profile saved successfully!");
    }


    // delete profile
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteProfile(@PathVariable String email) {
        profileService.deleteProfileByEmail(email);
        return ResponseEntity.ok(" Profile deleted successfully!");
    }

    // feign call
    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody UserSyncDto dto) {
        return profileService.saveUser(dto);
    }


    // get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update logged-in user profile (basic info)
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateMyProfile(@AuthenticationPrincipal Jwt jwt,
                                                           @RequestBody UserRequestDto dto) {
        String email = jwt.getSubject();
        return ResponseEntity.ok(userService.updateUser(email, dto));
    }

}

