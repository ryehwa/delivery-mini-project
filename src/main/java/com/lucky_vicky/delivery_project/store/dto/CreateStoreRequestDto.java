package com.lucky_vicky.delivery_project.store.dto;

public record CreateStoreRequestDto(
        String name,
        String address,
        String number
) {
}
