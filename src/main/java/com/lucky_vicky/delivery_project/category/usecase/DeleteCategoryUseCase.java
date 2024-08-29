package com.lucky_vicky.delivery_project.category.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;

import java.util.UUID;

@UseCase(value = "deleteCategoryUseCase")
public interface DeleteCategoryUseCase {
    void deleteStoreCategory(UUID uuid);
    void deleteLocalCategory(UUID uuid);
}
