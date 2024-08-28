package com.lucky_vicky.delivery_project.store.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;

import java.util.UUID;

@UseCase(value = "deleteStoreUseCase")
public interface DeleteStoreUseCase {
    void deleteStoreByAdmin(UUID storeId);
    void deleteStoreByOwner();
}
