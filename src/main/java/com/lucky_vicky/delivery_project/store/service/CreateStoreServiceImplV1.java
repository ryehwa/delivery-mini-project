package com.lucky_vicky.delivery_project.store.service;

import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.dto.CreateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.dto.StoreUUIDResponseDto;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.store.usecase.CreateStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CreateStoreServiceImplV1 implements CreateStoreUseCase {
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public StoreUUIDResponseDto createStore(CreateStoreRequestDto createStoreRequestDto) {
        Store newStore = Store.builder()
                .name(createStoreRequestDto.name())
                .address(createStoreRequestDto.address())
                .number(createStoreRequestDto.number())
                .build();
        storeRepository.save(newStore);
        StoreUUIDResponseDto responseDto = StoreUUIDResponseDto.fromEntity(newStore);
        return responseDto;
    }
}
