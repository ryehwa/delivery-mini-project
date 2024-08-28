package com.lucky_vicky.delivery_project.store.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.store.dto.StoreSummaryResponseDto;

import java.util.List;

@UseCase(value = "readStoreListUseCase")
public interface ReadStoreListUseCase {
    List<StoreSummaryResponseDto> readStoreList();
}
