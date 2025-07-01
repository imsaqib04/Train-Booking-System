package com.saqib.User_Service.controller;

import com.saqib.User_Service.model.User;
import org.springframework.http.ResponseEntity;
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

}