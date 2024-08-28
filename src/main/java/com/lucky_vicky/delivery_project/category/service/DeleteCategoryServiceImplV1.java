package com.lucky_vicky.delivery_project.category.service;

import com.lucky_vicky.delivery_project.category.domain.LocalCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.repository.LocalCategoryRepository;
import com.lucky_vicky.delivery_project.category.repository.StoreCategoryRepository;
import com.lucky_vicky.delivery_project.category.usecase.DeleteCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCategoryServiceImplV1 implements DeleteCategoryUseCase {
    private final StoreCategoryRepository storeCategoryRepository;
    private final LocalCategoryRepository localCategoryRepository;

    @Override
    public void deleteStoreCategory(UUID uuid) {
        StoreCategory storeCategory = storeCategoryRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게 카테고리입니다."));
        storeCategoryRepository.delete(storeCategory);
    }

    @Override
    public void deleteLocalCategory(UUID uuid) {
        LocalCategory localCategory = localCategoryRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역 카테고리입니다."));
        localCategoryRepository.delete(localCategory);
    }
}
