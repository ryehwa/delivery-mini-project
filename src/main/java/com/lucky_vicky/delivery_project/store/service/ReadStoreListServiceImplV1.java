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
        Page<Store> storePage = storeRepository.findAllByIsHiddeFalseAndIsDeletedFalse(pageable);

        return storePage.map(StoreSummaryResponseDto::fromEntity);
    }

    @Override
    public Page<StoreSummaryResponseDto> searchStore(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Store> storePage = storeRepository.findByIsHiddenFalseAndIsDeletedFalseAndNameContaining(text, pageable);
        return storePage.map(StoreSummaryResponseDto::fromEntity);
    }

    @Override
    public Page<StoreSummaryResponseDto> getStoresWithAverageRate(int page, int size, String sortBy, String orderBy) {

        Sort.Direction direction = orderBy.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Object[]> storePage = storeRepository.findStoresWithAverageRate(pageable);
        return storePage.map(objects -> {
            Store store = (Store) objects[0];
            Double averageRate = (Double) objects[1];
            return StoreSummaryResponseDto.builder()
                    .storeId(store.getId())
                    .name(store.getName())
                    .categories(store.getStoreCategoryMappers().stream()
                            .map(storeCategoryMapper -> storeCategoryMapper.getStoreCategory().getName())
                            .toList())
                    .averageRate(averageRate) // 평균 평점 추가
                    .build();
        });
    }
}
