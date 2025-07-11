//package com.saqib.Payment_Service.model;
//
//import com.saqib.Payment_Service.dto.UserDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserClient userClient;   // Feign to User‑Service
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserDto dto = userClient.getUserByEmail(email);
//        if (dto == null) {
//            throw new UsernameNotFoundException("User not found: " + email);
//        }
//
//        /* 🔑  Null‑safe role mapping */
//        List<SimpleGrantedAuthority> authorities = Optional
//                .ofNullable(dto.getRoles())          // roles could be null
//                .orElse(List.of("USER"))             // default role
//                .stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .toList();
//
//        return User.builder()
//                .username(dto.getEmail())
//                .password("")          // password irrelevant for JWT
//                .authorities(authorities)
//                .build();
//    }
//}
