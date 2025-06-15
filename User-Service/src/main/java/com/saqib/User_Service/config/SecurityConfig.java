package com.saqib.User_Service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf ().disable ()
                .authorizeHttpRequests ( auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/user/profile/save").authenticated()

                        .anyRequest ().authenticated ()
                )
                .oauth2ResourceServer ( oauth2 -> oauth2
                        .jwt ( jwt -> jwt.decoder ( jwtDecoder ) ) // custom JwtDecoder
                );

        return http.build ();
    }
}
