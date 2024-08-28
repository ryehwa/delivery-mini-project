package com.lucky_vicky.delivery_project.delivery.controller;

import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import com.lucky_vicky.delivery_project.delivery.dto.DeliveryResponseDto;
import com.lucky_vicky.delivery_project.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
