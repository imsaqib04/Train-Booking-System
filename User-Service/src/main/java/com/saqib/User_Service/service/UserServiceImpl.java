package com.saqib.User_Service.service;

import com.saqib.User_Service.dto.UserRequestDto;
import com.saqib.User_Service.dto.UserResponseDto;
import com.saqib.User_Service.model.User;
import com.saqib.User_Service.repo.UserRepository;
import com.saqib.User_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    @Override
    public UserResponseDto updateUser(String email, UserRequestDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setGender(dto.getGender());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setState(dto.getState());
        user.setPinCode(dto.getPinCode());
        user.setAadhaarNumber(dto.getAadhaarNumber());
        user.setPanNumber(dto.getPanNumber());
        userRepository.save(user);
        return mapToDto(user);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setPinCode(user.getPinCode());
        dto.setAadhaarNumber(user.getAadhaarNumber());
        dto.setPanNumber(user.getPanNumber());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        return dto;
    }
}
