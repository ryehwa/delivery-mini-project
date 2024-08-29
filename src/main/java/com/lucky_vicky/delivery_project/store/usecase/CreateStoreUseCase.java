package com.lucky_vicky.delivery_project.store.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.store.dto.CreateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.dto.StoreUUIDResponseDto;

import java.util.UUID;

@UseCase(value = "createStoreUseCase")
public interface CreateStoreUseCase {
    StoreUUIDResponseDto createStore(CreateStoreRequestDto createStoreRequestDto);
    StoreUUIDResponseDto acceptStore(UUID storeId);
}
