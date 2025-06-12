//package com.saqib.Auth_Service.security;
//
//import com.saqib.Auth_Service.model.User;
//import com.saqib.Auth_Service.repo.UserRepository;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//        String email = oauthUser.getAttribute("email");
//        String name = oauthUser.getAttribute("name");
//
//        // Check if user already exists
//        if (!userRepository.existsByEmail(email)) {
//            User user = new User();
//            user.setEmail(email);
//            user.setFullName(name);
//            user.setEnabled(true);
//            user.setRole("ROLE_USER");
//            userRepository.save(user);
//        }
//
//        // You can generate JWT token here and return if needed
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//}
