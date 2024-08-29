package com.lucky_vicky.delivery_project.store.dto;

import java.util.List;
import java.util.UUID;

public record CreateStoreRequestDto(
        String name,
        String address,
        String number,
        List<UUID> categoryIds // 카테고리 ID 리스트
) {
}
