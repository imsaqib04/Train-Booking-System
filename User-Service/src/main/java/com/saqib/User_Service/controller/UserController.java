package com.saqib.User_Service.controller;

import  com.saqib.User_Service.model.User;
import  com.saqib.User_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Add new user
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok ( userService.createUser ( user ) );
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById ( id )
                .map ( ResponseEntity::ok )
                .orElse ( ResponseEntity.notFound ().build () );
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok ( userService.getAllUsers () );
    }

    // Update user
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser ( id, userDetails );
        return ResponseEntity.ok ( updatedUser );
    }

    // Delete user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser ( id )) {
            return ResponseEntity.ok ( "User deleted successfully." );
        } else {
            return ResponseEntity.notFound ().build ();
        }
    }
}