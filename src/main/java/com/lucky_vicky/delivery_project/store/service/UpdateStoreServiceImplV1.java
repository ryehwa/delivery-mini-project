package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategoryMapper;
import com.lucky_vicky.delivery_project.category.repository.StoreCategoryRepository;
import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.StoreDetailResponseDto;
import com.lucky_vicky.delivery_project.store.dto.UpdateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.UpdateStoreUseCase;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateStoreServiceImplV1 implements UpdateStoreUseCase {
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public StoreDetailResponseDto updateStoreByAdmin(UUID storeId, UpdateStoreRequestDto request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        List<StoreCategoryMapper> storeCategoryMappers = getStoreCategoryMappers(request.categoryIds(), store);

        store.updateStoreInfo(request.name(), request.address(), request.number(), storeCategoryMappers);
        return StoreDetailResponseDto.fromEntity(store);
    }

    // 토큰 받기
    @Override
    public StoreDetailResponseDto updateStoreByOwner(UUID ownerId, UpdateStoreRequestDto request) {
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Store store = storeRepository.findByUser(user)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
        List<StoreCategoryMapper> storeCategoryMappers = getStoreCategoryMappers(request.categoryIds(), store);
        store.updateStoreInfo(request.name(), request.address(), request.number(), storeCategoryMappers);
        return StoreDetailResponseDto.fromEntity(store);
    }

    private List<StoreCategoryMapper> getStoreCategoryMappers(List<UUID> categoryIds, Store store) {
        List<StoreCategoryMapper> storeCategoryMappers = new ArrayList<>();
        for (UUID categoryId : categoryIds) {
            StoreCategory storeCategory = storeCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND));
            StoreCategoryMapper storeCategoryMapper = StoreCategoryMapper.builder()
                    .store(store)
                    .storeCategory(storeCategory)
                    .build();
            storeCategoryMappers.add(storeCategoryMapper);
        }
        return storeCategoryMappers;
    }
}
