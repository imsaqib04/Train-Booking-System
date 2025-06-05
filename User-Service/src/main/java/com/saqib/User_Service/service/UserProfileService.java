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
        return repo.save ( profile );
    }

    public UserProfile getProfileByUserId(String userId) {
        return repo.findById ( userId ).orElseThrow ();
    }

    public UserProfile findByEmail(String email) {
        return repo.findByEmail ( email );
    }

    public UserProfile updateProfile(String userId, UserProfile updatedProfile) {
        UserProfile existing = getProfileByUserId(userId);
        existing.setName(updatedProfile.getName());
        existing.setEmail(updatedProfile.getEmail());
        existing.setContactNumber(updatedProfile.getContactNumber());
        existing.setAddress(updatedProfile.getAddress());
        existing.setGender(updatedProfile.getGender());
        existing.setDob(updatedProfile.getDob());
        existing.setAadhaar(updatedProfile.getAadhaar());
        existing.setRole(updatedProfile.getRole());
        return repo.save(existing);
    }

    public void deleteProfile(String userId) {
        UserProfile profile = getProfileByUserId(userId);
        repo.delete(profile);
    }
}
