package com.saqib.Booking_Service.feign;

import com.saqib.Booking_Service.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserResponseDto getUserById(@PathVariable Long id);

}

