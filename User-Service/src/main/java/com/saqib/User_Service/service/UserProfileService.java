package com.saqib.User_Service.service;

import com.saqib.User_Service.model.UserProfile;
import com.saqib.User_Service.repo.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository repo;

    public UserProfile saveProfile(UserProfile profile) {
        return repo.save(profile);
    }

    public UserProfile getProfileByUserId(String userId) {
        return repo.findById(userId).orElseThrow();
    }

    public UserProfile findByEmail(String email) {
        return repo.findByEmail(email);
    }
}
