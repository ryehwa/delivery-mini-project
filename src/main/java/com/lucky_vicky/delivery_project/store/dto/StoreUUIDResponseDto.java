package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;


@Builder
public record StoreUUIDResponseDto(
        String storeId
) {
    public static StoreUUIDResponseDto fromEntity(Store storeEntity) {
        return StoreUUIDResponseDto.builder()
                .storeId(String.valueOf(storeEntity.getId()))
                .build();
    }
}
