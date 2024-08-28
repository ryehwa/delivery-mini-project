package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.StoreSummaryResponseDto;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.ReadStoreListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReadStoreListServiceImplV1 implements ReadStoreListUseCase {
    private final StoreRepository storeRepository;

    @Override
    public Page<StoreSummaryResponseDto> readStoreList(int page, int size, String sortBy, String orderBy) {

        Sort.Direction direction = orderBy.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Store> storePage = storeRepository.findAllByIsHiddeFalse(pageable);

        return storePage.map(StoreSummaryResponseDto::fromEntity);
    }

    @Override
    public Page<StoreSummaryResponseDto> searchStore(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Store> storePage = storeRepository.findByIsHiddenFalseAndNameContaining(text, pageable);
        return storePage.map(StoreSummaryResponseDto::fromEntity);
    }
}
