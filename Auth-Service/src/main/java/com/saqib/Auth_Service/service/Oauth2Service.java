//package com.saqib.Auth_Service.service;
//
//import com.saqib.Auth_Service.model.Role;
//import com.saqib.Auth_Service.model.User;
//import com.saqib.Auth_Service.repo.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Service
//public class Oauth2Service extends DefaultOAuth2UserService {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oauthUser = super.loadUser(userRequest);
//
//        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase(); // GOOGLE, GITHUB
//        String email = oauthUser.getAttribute("email");
//        String name = oauthUser.getAttribute("name");
//
//        if (email == null) {
//            throw new OAuth2AuthenticationException("Email not available from OAuth2 provider");
//        }
//
//        Optional<User> existingUser = userRepo.findByEmail ( email );
//
//        if (existingUser.isEmpty()) {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setUsername(name != null ? name : "Unknown User");
//            newUser.setProvider(provider);
//            newUser.setEmailVerified(true); // optional
//            newUser.setPassword("N/A");     // Not used in OAuth2 login
//            newUser.setRole(Role.Role_USER);
//            newUser.setEnabled(true);
//            newUser.setLocked(false);
//// ✅ Fix added here
//            userRepo.save(newUser);
//        }
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                oauthUser.getAttributes(),
//                "email"
//        );
//    }
//}


package com.saqib.Auth_Service.service;

import com.saqib.Auth_Service.model.Role;
import com.saqib.Auth_Service.model.User;
import com.saqib.Auth_Service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class Oauth2Service extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Detect provider
        String email;
        String name;

        String registrationId = request.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google")) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
        } else if (registrationId.equals("github")) {
            email = (String) attributes.get("email"); // May be null in GitHub
            name = (String) attributes.get("name");
            if (email == null) email = name + "@github.com"; // fallback
        } else {
            throw new RuntimeException("Unsupported provider: " + registrationId);
        }

        // Save if new user
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setUsername (name);
            user.setPassword("OAUTH2_USER");
            user.setEnabled(true); // ✅ AUTO VERIFIED
            user.setRole( Role.valueOf ( "USER" ) );

            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "name"
        );
    }
}
