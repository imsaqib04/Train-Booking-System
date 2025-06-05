package com.saqib.Auth_Service.controller;

import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return authService.register ( user );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userMap) {
        String email = userMap.get ( "email" );
        String password = userMap.get ( "password" );

        String token = authService.login ( email, password );

        if (token == null) {
            return ResponseEntity.status ( 401 ).body ( "Invalid email or password" );
        }
        return ResponseEntity.ok ( Map.of ( "token", token ) );
    }
}