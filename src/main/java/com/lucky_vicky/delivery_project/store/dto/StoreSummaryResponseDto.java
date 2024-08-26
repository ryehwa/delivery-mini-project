package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;

@Builder
public record StoreSummaryResponseDto(
        String storeId,
        String name
) {
    public static StoreSummaryResponseDto fromEntity(Store store) {
        return StoreSummaryResponseDto.builder()
                .storeId(String.valueOf(store.getId()))
                .name(store.getName())
                .build();
    }
}
