package com.saqib.User_Service.service;

import com.saqib.User_Service.dto.UserRequestDto;
import com.saqib.User_Service.dto.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    UserResponseDto getUserByEmail(String email);
    UserResponseDto updateUser(String email, UserRequestDto dto);
    void deleteUser(String email);
}
