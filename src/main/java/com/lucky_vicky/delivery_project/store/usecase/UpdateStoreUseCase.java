package com.lucky_vicky.delivery_project.store.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.store.dto.StoreDetailResponseDto;
import com.lucky_vicky.delivery_project.store.dto.UpdateStoreRequestDto;

import java.util.UUID;

@UseCase(value = "updateStoreUseCase")
public interface UpdateStoreUseCase {
    StoreDetailResponseDto updateStoreByAdmin(UUID storeId, UpdateStoreRequestDto updateStoreRequestDto);
    StoreDetailResponseDto updateStoreByOwner(UUID userId, UpdateStoreRequestDto updateStoreRequestDto);
}
