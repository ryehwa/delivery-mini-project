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
     * 가게 목록 조회
     * PUBLIC
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @GetMapping("")
    public ResponseEntity<?> readStoreList() {
        return ResponseEntity.ok(readStoreListUseCase.readStoreList());
    }

    /**
     * 가게 상세 조회
     * PUBLIC
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @GetMapping("/{storeId}")
    public ResponseEntity<?> readStoreDetail(
            @PathVariable UUID storeId
    ) {
        return ResponseEntity.ok(readStoreDetailUseCase.readStoreDetail(storeId));
    }

    /**
     * 가게 정보 변경
     * STORE
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @PutMapping("")
    public ResponseEntity<?> updateStoreByOwner(
            // 토큰 받기
            @RequestBody UpdateStoreRequestDto updateStoreRequestDto
    ) {
        return ResponseEntity.ok(updateStoreUseCase.updateStoreByOwner(updateStoreRequestDto));
    }

    /**
     * 가게 삭제
     * STORE
     * */
    @DeleteMapping("")
    public ResponseEntity<?> deleteStoreByOwner(
            // 토큰 받기
            @PathVariable UUID storeId
    ) {
        deleteStoreUseCase.deleteStoreByOwner(storeId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 가게 생성 요청
     * ADMIN, STORE
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @PostMapping("")
    public ResponseEntity<?> createStore(
            @RequestBody CreateStoreRequestDto createStoreRequestDto
    ) {
        return ResponseEntity.ok(createStoreUseCase.createStore(createStoreRequestDto));
    }

    /**
     * 가게 생성 수락
     * ADMIN
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @PostMapping("/{storeId}/acceptance")
    public ResponseEntity<?> acceptStore(@PathVariable UUID storeId) {
        return ResponseEntity.ok(createStoreUseCase.acceptStore(storeId));
    }

    /**
     * 가게 정보 변경
     * ADMIN
     * 카테고리 추가, 인증/인가 추가 해야함.
     * */
    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateStoreByAdmin(
            @PathVariable UUID storeId,
            @RequestBody UpdateStoreRequestDto updateStoreRequestDto
    ) {
        return ResponseEntity.ok(updateStoreUseCase.updateStoreByAdmin(storeId, updateStoreRequestDto));
    }

    /**
     * 가게 삭제
     * ADMIN
     * */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStoreByAdmin(
            @PathVariable UUID storeId
    ) {
        deleteStoreUseCase.deleteStoreByAdmin(storeId);
        return ResponseEntity.noContent().build();
    }

}
