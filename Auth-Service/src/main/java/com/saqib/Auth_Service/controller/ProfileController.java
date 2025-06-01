package com.saqib.Auth_Service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public ResponseEntity<String> getProfile(Authentication authentication) {
        return ResponseEntity.ok("Welcome, " + authentication.getName());
    }
}
