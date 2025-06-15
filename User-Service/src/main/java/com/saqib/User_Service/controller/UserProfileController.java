package com.saqib.User_Service.controller;


import com.saqib.User_Service.dto.ResponseDtoForEmail;
import com.saqib.User_Service.dto.UserProfileRequestDto;
import com.saqib.User_Service.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrUpdateProfile(@RequestBody UserProfileRequestDto requestDto) {

        profileService.saveOrUpdateProfile(requestDto);
        return ResponseEntity.ok("Profile saved successfully!");
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteProfile(@PathVariable String email) {
        profileService.deleteProfileByEmail(email);
        return ResponseEntity.ok(" Profile deleted successfully!");
    }

//    @PostMapping("/save")
//    public ResponseEntity<String>saveData(@RequestBody ResponseDtoForEmail responseDtoForEmail){
//        profileService.save(responseDtoForEmail);
//        return ResponseEntity.ok ("save data");
//    }

//    @PostMapping("/save")
//    public ResponseEntity<String> saveUser(@RequestBody ResponseDtoForEmail dto,
//                                           @RequestHeader("Authorization") String token) {
//        return profileService.saveUser(dto);
//    }
    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody ResponseDtoForEmail dto) {
        return profileService.saveUser(dto);
    }
}
