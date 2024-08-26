package com.lucky_vicky.delivery_project.store.controller;

import com.lucky_vicky.delivery_project.store.dto.CreateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.dto.UpdateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.usecase.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stores")
@Slf4j
@RequiredArgsConstructor
public class StoreController {
    private final CreateStoreUseCase createStoreUseCase;
    private final ReadStoreDetailUseCase readStoreDetailUseCase;
    private final ReadStoreListUseCase readStoreListUseCase;
    private final UpdateStoreUseCase updateStoreUseCase;
    private final DeleteStoreUseCase deleteStoreUseCase;

    /**
     * 가게 생성
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @PostMapping("")
    public ResponseEntity<?> createStore(
            @RequestBody CreateStoreRequestDto createStoreRequestDto
    ) {
        return ResponseEntity.ok(createStoreUseCase.createStore(createStoreRequestDto));
    }

    /**
     * 가게 목록 조회
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @GetMapping("")
    public ResponseEntity<?> readStoreList() {
        return ResponseEntity.ok(readStoreListUseCase.readStoreList());
    }

    /**
     * 가게 상세 조회
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @GetMapping("/{storeId}")
    public ResponseEntity<?> readStoreDetail(
            @PathVariable String storeId
    ) {
        return ResponseEntity.ok(readStoreDetailUseCase.readStoreDetail(UUID.fromString(storeId)));
    }

    /**
     * 가게 정보 변경
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateStore(
            @PathVariable String storeId,
            @RequestBody UpdateStoreRequestDto updateStoreRequestDto
    ) {
        return ResponseEntity.ok(updateStoreUseCase.updateStore(UUID.fromString(storeId), updateStoreRequestDto));
    }

    /**
     * 가게 삭제
     * */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(
            @PathVariable String storeId
    ) {
        deleteStoreUseCase.deleteStore(UUID.fromString(storeId));
        return ResponseEntity.noContent().build();
    }

}
