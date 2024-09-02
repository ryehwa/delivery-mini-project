package com.lucky_vicky.delivery_project.category.dto;

import com.lucky_vicky.delivery_project.category.domain.LocalCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.domain.type.CategoryType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryResponseDto(
        UUID categoryId,
        String name,
        CategoryType type
) {
    public static CategoryResponseDto fromEntity(StoreCategory storeCategory) {
        return CategoryResponseDto.builder()
                .categoryId(storeCategory.getId())
                .name(storeCategory.getName())
                .type(CategoryType.STORE)
                .build();
    }

    public static CategoryResponseDto fromEntity(LocalCategory localCategory) {
        return CategoryResponseDto.builder()
                .categoryId(localCategory.getId())
                .name(localCategory.getName())
                .type(CategoryType.LOCAL)
                .build();
    }
}
