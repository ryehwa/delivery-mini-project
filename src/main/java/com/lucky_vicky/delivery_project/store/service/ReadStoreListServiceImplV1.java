package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.StoreSummaryResponseDto;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.ReadStoreListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReadStoreListServiceImplV1 implements ReadStoreListUseCase {
    private final StoreRepository storeRepository;

    @Override
    public List<StoreSummaryResponseDto> readStoreList() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreSummaryResponseDto> storeSummaryResponseDtoList = storeList.stream()
                .map(StoreSummaryResponseDto::fromEntity)
                .toList();
        return storeSummaryResponseDtoList;
    }
}
