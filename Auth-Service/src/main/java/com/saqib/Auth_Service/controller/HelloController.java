package com.saqib.Auth_Service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Hello World! OAuth2 Login Successful ✅";
    }
}

