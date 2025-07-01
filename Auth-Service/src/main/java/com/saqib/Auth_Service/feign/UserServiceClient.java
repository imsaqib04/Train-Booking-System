package com.saqib.Auth_Service.feign;

import com.saqib.Auth_Service.dto.UserSyncDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8092/api/user/profile")
public interface UserServiceClient {

        @PostMapping("/save")
        ResponseEntity<String> saveUser(@RequestBody UserSyncDto userSyncDto,
                                        @RequestHeader("Authorization") String token);
    }

