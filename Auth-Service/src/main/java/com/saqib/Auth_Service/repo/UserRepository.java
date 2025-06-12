package com.saqib.Auth_Service.repo;

import com.saqib.Auth_Service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional findByEmail(String email);
}
