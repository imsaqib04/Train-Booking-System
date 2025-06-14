package com.saqib.User_Service.controller;

import org.springframework.security.oauth2.jwt.Jwt; //Correct
import com.saqib.User_Service.dto.UserRequestDto;
import com.saqib.User_Service.dto.UserResponseDto;
import com.saqib.User_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserResponseDto getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        return userService.getUserByEmail(jwt.getSubject());
    }

    @PutMapping("/me")
    public UserResponseDto updateMyProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody UserRequestDto dto) {
        return userService.updateUser(jwt.getSubject(), dto);
    }

    @DeleteMapping("/me")
    public String deleteMyProfile(@AuthenticationPrincipal Jwt jwt) {
        userService.deleteUser(jwt.getSubject());
        return "User deleted successfully";
    }
}
