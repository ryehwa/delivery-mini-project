package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;

import java.util.UUID;


@Builder
public record StoreUUIDResponseDto(
        UUID storeId
) {
    public static StoreUUIDResponseDto fromEntity(Store storeEntity) {
        return StoreUUIDResponseDto.builder()
                .storeId(storeEntity.getId())
                .build();
    }
}
