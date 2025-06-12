package com.saqib.Auth_Service.config;

import com.saqib.Auth_Service.service.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private Oauth2Service customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf ().disable ()
                .authorizeHttpRequests ( auth -> auth
                        .requestMatchers ( "/api/auth/**", "/oauth2/**", "/welcome" ).permitAll ()
                        .anyRequest ().authenticated ()
                )
                .oauth2Login ( oauth2 -> oauth2
                        .loginPage ( "/oauth2/authorization/google" )  // chanage here for google or github
                        .userInfoEndpoint ( userInfo -> userInfo.userService ( customOAuth2UserService ) )
                        .defaultSuccessUrl ( "/welcome", true )
                        .failureUrl ( "/oauth2/loginFailure" )
                );

        return http.build ();
    }
}
