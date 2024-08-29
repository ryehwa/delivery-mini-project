package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record StoreDetailResponseDto(
        String storeId,
        String name,
        String address,
        String number,
        List<String> categories // 카테고리 리스트
) {
    public static StoreDetailResponseDto fromEntity(Store store) {
        return StoreDetailResponseDto.builder()
                .storeId(String.valueOf(store.getId()))
                .name(store.getName())
                .address(store.getAddress())
                .number(store.getNumber())
                .categories(store.getStoreCategoryMappers().stream()
                        .map(storeCategoryMapper -> storeCategoryMapper.getStoreCategory().getName())
                        .toList())
                .build();
    }
}
