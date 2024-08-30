package com.lucky_vicky.delivery_project.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}