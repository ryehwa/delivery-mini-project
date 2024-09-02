package com.lucky_vicky.delivery_project.category.usecase;

import com.lucky_vicky.delivery_project.category.dto.CategoryResponseDto;
import com.lucky_vicky.delivery_project.category.dto.CreateCategoryRequestDto;
import com.lucky_vicky.delivery_project.global.annotation.UseCase;

@UseCase(value = "createCategoryUseCase")
public interface CreateCategoryUseCase {
    CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto);
}
