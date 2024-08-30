package com.lucky_vicky.delivery_project.store.dto;

import java.util.List;
import java.util.UUID;

public record UpdateStoreRequestDto(
        String name,
        String address,
        String number,
        List<UUID> categoryIds
) {
}
