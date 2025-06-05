package com.saqib.User_Service.repo;

import com.saqib.User_Service.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    UserProfile findByEmail(String email);
}
