package com.lucky_vicky.delivery_project.store.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.store.dto.StoreDetailResponseDto;

import java.util.UUID;

@UseCase(value = "readStoreDetailUseCase")
public interface ReadStoreDetailUseCase {
    StoreDetailResponseDto readStoreDetail(UUID storeId);
}
