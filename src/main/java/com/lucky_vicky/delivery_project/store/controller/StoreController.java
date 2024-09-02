package com.lucky_vicky.delivery_project.store.controller;

import com.lucky_vicky.delivery_project.global.annotation.UserId;
import com.lucky_vicky.delivery_project.store.dto.CreateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.dto.UpdateStoreRequestDto;
import com.lucky_vicky.delivery_project.store.usecase.*;
import com.lucky_vicky.delivery_project.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * */
    @GetMapping("")
    public ResponseEntity<?> readStoreList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "DESC") String orderBy
    ) {
        return ResponseEntity.ok(readStoreListUseCase.readStoreList(page, size, sortBy, orderBy));
    }

    /**
     * 가게 검색
     * PUBLIC
     * */
    @GetMapping("/search")
    public ResponseEntity<?> searchStore(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "text") String text
    ) {
        return ResponseEntity.ok(readStoreListUseCase.searchStore(page, size, text));
    }

    /**
     * 가게 상세 조회
     * PUBLIC
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
     * */
    @PutMapping("")
    public ResponseEntity<?> updateStoreByOwner(
            @UserId UUID userId,
            @RequestBody UpdateStoreRequestDto updateStoreRequestDto
    ) {
        return ResponseEntity.ok(updateStoreUseCase.updateStoreByOwner(userId, updateStoreRequestDto));
    }

    /**
     * 가게 삭제
     * STORE
     * */
    @DeleteMapping("")
    public ResponseEntity<?> deleteStoreByOwner(
            @UserId UUID userId
    ) {
        deleteStoreUseCase.deleteStoreByOwner(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 가게 생성 요청
     * ADMIN, STORE
     * */
    @PreAuthorize("hasRole('ADMIN') or hasRole('STORE')")
    @PostMapping("")
    public ResponseEntity<?> createStore(
            @RequestBody CreateStoreRequestDto createStoreRequestDto, @AuthenticationPrincipal UserPrincipal userPrincipal
            ) {

        UUID userId = userPrincipal.getId();
        return ResponseEntity.ok(createStoreUseCase.createStore(createStoreRequestDto, userId));
    }

    /**
     * 가게 생성 수락
     * ADMIN
     * */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{storeId}/acceptance")
    public ResponseEntity<?> acceptStore(@PathVariable UUID storeId) {
        return ResponseEntity.ok(createStoreUseCase.acceptStore(storeId));
    }

    /**
     * 가게 정보 변경
     * ADMIN
     * */
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStoreByAdmin(
            @PathVariable UUID storeId
    ) {
        deleteStoreUseCase.deleteStoreByAdmin(storeId);
        return ResponseEntity.noContent().build();
    }

}
