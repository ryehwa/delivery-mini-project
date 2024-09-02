package com.lucky_vicky.delivery_project.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(
        @NotBlank String name,
        @NotBlank String type
) {
}
