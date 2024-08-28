package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.StoreDetailResponseDto;
import com.lucky_vicky.delivery_project.store.dto.UpdateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.UpdateStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateStoreServiceImplV1 implements UpdateStoreUseCase {
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public StoreDetailResponseDto updateStoreByAdmin(UUID storeId, UpdateStoreRequestDto request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게가 존재하지 않습니다."));
        store.updateStoreInfo(request.name(), request.address(), request.number());
        return StoreDetailResponseDto.fromEntity(store);
    }

    // 토큰 받기
    @Override
    public StoreDetailResponseDto updateStoreByOwner(UpdateStoreRequestDto request) {
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new IllegalArgumentException("가게가 존재하지 않습니다."));
//        store.updateStoreInfo(request.name(), request.address(), request.number());
//        return StoreDetailResponseDto.fromEntity(store);
        return null;
    }
}
