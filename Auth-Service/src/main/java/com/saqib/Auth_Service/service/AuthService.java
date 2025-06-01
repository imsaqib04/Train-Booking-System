//package com.saqib.Auth_Service.service;
//
//import com.saqib.Auth_Service.model.User;
//import com.saqib.Auth_Service.repo.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder (12);
//
//    public User saveUser(User user){
//        user.setPassword ( encoder.encode ( user.getPassword () ));
//        System.out.println (user.getPassword ());
//        return userRepository.save ( user );
//    }
//}
