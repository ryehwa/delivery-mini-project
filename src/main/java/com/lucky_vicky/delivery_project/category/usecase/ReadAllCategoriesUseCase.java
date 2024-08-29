package com.lucky_vicky.delivery_project.category.usecase;

import com.lucky_vicky.delivery_project.category.dto.CategoryResponseDto;
import com.lucky_vicky.delivery_project.global.annotation.UseCase;

import java.util.List;

@UseCase(value = "readAllCategoriesUseCase")
public interface ReadAllCategoriesUseCase {
    List<CategoryResponseDto> readAllStoreCategories();
    List<CategoryResponseDto> readAllLocalCategories();
}
