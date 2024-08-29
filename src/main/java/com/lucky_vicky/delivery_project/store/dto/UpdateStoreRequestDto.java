package com.lucky_vicky.delivery_project.store.dto;

public record UpdateStoreRequestDto(
        String name,
        String address,
        String number,
        String category
) {
}
