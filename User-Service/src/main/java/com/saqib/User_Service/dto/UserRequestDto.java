package com.saqib.User_Service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class UserRequestDto {
        @NotBlank
        private String fullName;

        @Email
        @NotBlank
        private String email;

        private String phoneNumber;
        private String gender;
        private LocalDate dateOfBirth;

        private String address;
        private String city;
        private String state;
        private Long pinCode;

        private String aadhaarNumber;
        private String panNumber;

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getGender() {
                return gender;
        }

        public void setGender(String gender) {
                this.gender = gender;
        }

        public LocalDate getDateOfBirth() {
                return dateOfBirth;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getState() {
                return state;
        }

        public void setState(String state) {
                this.state = state;
        }

        public Long getPinCode() {
                return pinCode;
        }

        public void setPinCode(Long pinCode) {
                this.pinCode = pinCode;
        }

        public String getAadhaarNumber() {
                return aadhaarNumber;
        }

        public void setAadhaarNumber(String aadhaarNumber) {
                this.aadhaarNumber = aadhaarNumber;
        }

        public String getPanNumber() {
                return panNumber;
        }

        public void setPanNumber(String panNumber) {
                this.panNumber = panNumber;
        }
}
