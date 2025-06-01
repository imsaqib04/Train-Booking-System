package com.saqib.User_Service.service;


import com.saqib.User_Service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saqib.User_Service.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    // Create a new user
    public User createUser(User user) {
        return userRepository.save ( user );
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById ( id );
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll ();
    }

    // Update user details
    public User updateUser(Long id, User userDetails) {
        Optional<User> existingUserOpt = userRepository.findById ( id );

        if (existingUserOpt.isPresent ()) {
            User existingUser = existingUserOpt.get ();
            existingUser.setUsername ( userDetails.getUsername () );
            existingUser.setEmail ( userDetails.getEmail () );
            existingUser.setDob ( userDetails.getDob () );
            existingUser.setPhoneNumber ( userDetails.getPhoneNumber () );
            existingUser.setAddress ( userDetails.getAddress () );
            existingUser.setGender ( userDetails.getGender () );
            return userRepository.save ( existingUser );
        }

        return null;
    }

    // Delete user by ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById ( id )) {
            userRepository.deleteById ( id );
            return true;
        }
        return false;
    }
}
