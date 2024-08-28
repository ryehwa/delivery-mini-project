package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import com.lucky_vicky.delivery_project.category.domain.StoreCategoryMapper;
import com.lucky_vicky.delivery_project.category.repository.StoreCategoryRepository;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.CreateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.dto.StoreUUIDResponseDto;
import com.lucky_vicky.delivery_project.store.repository.StoreCategoryMapperRepository;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.CreateStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CreateStoreServiceImplV1 implements CreateStoreUseCase {
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreCategoryMapperRepository storeCategoryMapperRepository;

    @Override
    @Transactional
    public StoreUUIDResponseDto createStore(CreateStoreRequestDto createStoreRequestDto) {
        // Store 객체 생성 및 저장
        Store newStore = Store.builder()
                .name(createStoreRequestDto.name())
                .address(createStoreRequestDto.address())
                .number(createStoreRequestDto.number())
                .build();
        storeRepository.save(newStore);

        // 카테고리 매핑 처리
        List<UUID> categoryIds = createStoreRequestDto.categoryIds();
        List<StoreCategoryMapper> storeCategoryMappers = new ArrayList<>();

        for (UUID categoryId : categoryIds) {
            StoreCategory storeCategory = storeCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
            StoreCategoryMapper storeCategoryMapper = StoreCategoryMapper.builder()
                    .store(newStore)
                    .storeCategory(storeCategory)
                    .build();
            storeCategoryMappers.add(storeCategoryMapper);
        }

        storeCategoryMapperRepository.saveAll(storeCategoryMappers); // StoreCategoryMapper 저장

        StoreUUIDResponseDto responseDto = StoreUUIDResponseDto.fromEntity(newStore);
        return responseDto;
    }

    @Override
    @Transactional
    public StoreUUIDResponseDto acceptStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));
        store.updateStoreStatus();
        return StoreUUIDResponseDto.fromEntity(store);
    }
}
