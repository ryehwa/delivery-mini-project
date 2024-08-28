package com.lucky_vicky.delivery_project.category.dto;

import com.lucky_vicky.delivery_project.category.domain.LocalCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.domain.type.CategoryType;
import lombok.Builder;

@Builder
public record CategoryResponseDto(
        String categoryId,
        String name,
        CategoryType type
) {
    public static CategoryResponseDto fromEntity(StoreCategory storeCategory) {
        return CategoryResponseDto.builder()
                .categoryId(String.valueOf(storeCategory.getId()))
                .name(storeCategory.getName())
                .type(CategoryType.STORE)
                .build();
    }

    public static CategoryResponseDto fromEntity(LocalCategory localCategory) {
        return CategoryResponseDto.builder()
                .categoryId(String.valueOf(localCategory.getId()))
                .name(localCategory.getName())
                .type(CategoryType.LOCAL)
                .build();
    }
}
