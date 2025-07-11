//package com.saqib.Payment_Service.feign;
//
//
//import com.saqib.Payment_Service.dto.UserDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//    @FeignClient(name = "user-service", path = "/api/internal/users")
//    public interface UserClient {
//
//        @GetMapping("/email/{email}")
//        UserDto getUserByEmail(@PathVariable("email") String email);
//
//        @GetMapping("/{id}")
//        UserDto getUserById(@PathVariable("id") Long id);
//    }
//
