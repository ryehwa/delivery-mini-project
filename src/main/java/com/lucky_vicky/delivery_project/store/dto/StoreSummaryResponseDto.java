package com.lucky_vicky.delivery_project.store.dto;

import com.lucky_vicky.delivery_project.store.domain.Store;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record StoreSummaryResponseDto(
        UUID storeId,
        String name,
        List<String> categories // 카테고리 이름
) {
    public static StoreSummaryResponseDto fromEntity(Store store) {
        return StoreSummaryResponseDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .categories(store.getStoreCategoryMappers().stream()
                        .map(storeCategoryMapper -> storeCategoryMapper.getStoreCategory().getName())
                        .toList())
                .build();
    }
}
