package com.lucky_vicky.delivery_project.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String nickname;
    private String address;
    private String phoneNumber;
    private Set<String> roles;
}