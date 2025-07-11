package com.saqib.Payment_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                /* 1️⃣  csrf पूरी REST app के लिये off */
                .csrf( AbstractHttpConfigurer::disable)

                /* 2️⃣  किसी तरह का login/session नहीं चाहिये */
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                /* 3️⃣  route rules */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( HttpMethod.POST,"/api/payments/order").permitAll()
                        .anyRequest().permitAll());

        return http.build();
    }
}
