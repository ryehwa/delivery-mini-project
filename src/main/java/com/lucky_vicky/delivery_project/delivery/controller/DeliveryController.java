package com.lucky_vicky.delivery_project.delivery.controller;

import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryResponseDto;
import com.lucky_vicky.delivery_project.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity createDelivery(@RequestBody DeliveryRequestDto deliveryRequestDto) {
        DeliveryResponseDto deliveryResponseDto = deliveryService.createAddress(deliveryRequestDto);
        return new ResponseEntity<>(deliveryResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getDeliveryList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort) {
        Page<DeliveryResponseDto> deliveryList = deliveryService.getDeliveryList(page-1, size, sort);
        return ResponseEntity.ok(deliveryList);
    }

    // 배송지 수정
    @PutMapping("/{deliveryId}")
    public ResponseEntity updateDelivery(
            @PathVariable("deliveryId") UUID deliveryId,
            @RequestBody DeliveryRequestDto deliveryRequestDto){
        DeliveryResponseDto deliveryResponseDto = deliveryService.updateDelivery(deliveryId, deliveryRequestDto);
        return ResponseEntity.ok(deliveryResponseDto);
    }

    // 배송지 삭제
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity deleteDelivery(@PathVariable("deliveryId") UUID deliveryId){
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }
}
