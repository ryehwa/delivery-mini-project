package com.lucky_vicky.delivery_project.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateStoreRequestDto(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String number,
        List<UUID> categoryIds // 카테고리 ID 리스트
) {
}
