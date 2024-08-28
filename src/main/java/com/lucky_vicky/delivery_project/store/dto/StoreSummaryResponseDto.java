package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;

import java.util.UUID;

@Builder
public record StoreSummaryResponseDto(
        UUID storeId,
        String name
) {
    public static StoreSummaryResponseDto fromEntity(Store store) {
        return StoreSummaryResponseDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .build();
    }
}
