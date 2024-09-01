package com.lucky_vicky.delivery_project.category.service;

import com.lucky_vicky.delivery_project.category.domain.LocalCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.domain.type.CategoryType;
import com.lucky_vicky.delivery_project.category.dto.CategoryResponseDto;
import com.lucky_vicky.delivery_project.category.dto.CreateCategoryRequestDto;
import com.lucky_vicky.delivery_project.category.repository.LocalCategoryRepository;
import com.lucky_vicky.delivery_project.category.repository.StoreCategoryRepository;
import com.lucky_vicky.delivery_project.category.usecase.CreateCategoryUseCase;
import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCategoryServiceImplV1 implements CreateCategoryUseCase {
    private final StoreCategoryRepository storeCategoryRepository;
    private final LocalCategoryRepository localCategoryRepository;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        String type = createCategoryRequestDto.type().toUpperCase();
        CategoryResponseDto categoryResponseDto;
        if (type.equals(CategoryType.STORE.getCategoryType())) {
            categoryResponseDto = createStoreCategory(createCategoryRequestDto);
        } else if (type.equals(CategoryType.LOCAL.getCategoryType())) {
            categoryResponseDto = createLocalCategory(createCategoryRequestDto);
        } else {
            throw new BusinessLogicException(ExceptionCode.INVALID_CATEGORY_TYPE);
        }
        return categoryResponseDto;
    }

    private CategoryResponseDto createStoreCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        StoreCategory storeCategory = StoreCategory.builder()
                .name(createCategoryRequestDto.name())
                .build();
        return CategoryResponseDto.fromEntity(storeCategory);
    }

    private CategoryResponseDto createLocalCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        LocalCategory localCategory = LocalCategory.builder()
                .name(createCategoryRequestDto.name())
                .build();
        return CategoryResponseDto.fromEntity(localCategory);
    }
}
