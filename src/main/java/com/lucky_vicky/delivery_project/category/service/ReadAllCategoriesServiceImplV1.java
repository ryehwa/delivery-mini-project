package com.lucky_vicky.delivery_project.category.service;

import com.lucky_vicky.delivery_project.category.domain.LocalCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.dto.CategoryResponseDto;
import com.lucky_vicky.delivery_project.category.repository.LocalCategoryRepository;
import com.lucky_vicky.delivery_project.category.repository.StoreCategoryRepository;
import com.lucky_vicky.delivery_project.category.usecase.ReadAllCategoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllCategoriesServiceImplV1 implements ReadAllCategoriesUseCase {
    private final StoreCategoryRepository storeCategoryRepository;
    private final LocalCategoryRepository localCategoryRepository;

    @Override
    public List<CategoryResponseDto> readAllStoreCategories() {
        List<StoreCategory> storeCategoryList = storeCategoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = storeCategoryList.stream()
                .map(storeCategory -> CategoryResponseDto.fromEntity(storeCategory))
                .toList();
        return categoryResponseDtoList;
    }

    @Override
    public List<CategoryResponseDto> readAllLocalCategories() {
        List<LocalCategory> localCategoryList = localCategoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = localCategoryList.stream()
                .map(localCategory -> CategoryResponseDto.fromEntity(localCategory))
                .toList();
        return categoryResponseDtoList;
    }
}


