package com.lucky_vicky.delivery_project.user.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String nickname;
    private String address;
    private String phoneNumber;
}