package com.saqib.Auth_Service.feign;

import com.saqib.Auth_Service.dto.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "User-Service", url = "http://localhost:8082/users")
    public interface UserServiceClient {
        @PostMapping("/save")
        public UserProfile saveUser(@RequestBody UserProfile profile);

        @GetMapping("/{userId}")
        public UserProfile getUserById(@PathVariable("userId") String userId);

        @GetMapping("/email/{email}")
        UserProfile findByEmail(@PathVariable("email") String email);
}