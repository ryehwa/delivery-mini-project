package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;

@Builder
public record StoreDetailResponseDto(
        String storeId,
        String name,
        String address,
        String number
        // 카테고리 추가해야 함.
) {
    public static StoreDetailResponseDto fromEntity(Store store) {
        return StoreDetailResponseDto.builder()
                .storeId(String.valueOf(store.getId()))
                .name(store.getName())
                .address(store.getAddress())
                .number(store.getNumber())
                .build();
    }
}
