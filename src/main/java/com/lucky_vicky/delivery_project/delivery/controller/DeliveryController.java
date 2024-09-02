package com.lucky_vicky.delivery_project.delivery.controller;

import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryResponseDto;
import com.lucky_vicky.delivery_project.delivery.service.DeliveryService;
import com.lucky_vicky.delivery_project.user.domain.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity createDelivery(@CurrentUser String username,
                                         @RequestBody DeliveryRequestDto deliveryRequestDto) {
        DeliveryResponseDto deliveryResponseDto = deliveryService.createAddress(username, deliveryRequestDto);
        return new ResponseEntity<>(deliveryResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getDeliveryList(
            @CurrentUser String username,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort) {
        Page<DeliveryResponseDto> deliveryList = deliveryService.getDeliveryList(username, page, size, sort);
        return ResponseEntity.ok(deliveryList);
    }

    // 배송지 수정
    @PutMapping("/{deliveryId}")
    public ResponseEntity updateDelivery(
            @CurrentUser String username,
            @PathVariable("deliveryId") UUID deliveryId,
            @RequestBody DeliveryRequestDto deliveryRequestDto){
        DeliveryResponseDto deliveryResponseDto = deliveryService.updateDelivery(username, deliveryId, deliveryRequestDto);
        return ResponseEntity.ok(deliveryResponseDto);
    }

    // 배송지 삭제
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity deleteDelivery(@CurrentUser String username, @PathVariable("deliveryId") UUID deliveryId){
        deliveryService.deleteDelivery(username, deliveryId);
        return ResponseEntity.noContent().build();
    }
}
