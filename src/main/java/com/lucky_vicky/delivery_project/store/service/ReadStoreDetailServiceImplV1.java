package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.StoreDetailResponseDto;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.ReadStoreDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadStoreDetailServiceImplV1 implements ReadStoreDetailUseCase {

    private final StoreRepository storeRepository;

    // 카테고리 추가해야 함.
    @Override
    public StoreDetailResponseDto readStoreDetail(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게가 존재하지 않습니다."));

        StoreDetailResponseDto storeDetailResponseDto = StoreDetailResponseDto.builder()
                .storeId(String.valueOf(store.getId()))
                .address(store.getAddress())
                .name(store.getName())
                .number(store.getNumber())
                .build();
        return storeDetailResponseDto;
    }
}
