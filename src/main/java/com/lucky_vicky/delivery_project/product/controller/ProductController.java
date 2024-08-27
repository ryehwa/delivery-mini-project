package com.lucky_vicky.delivery_project.product.controller;

import com.lucky_vicky.delivery_project.global.util.CustomPageResponse;
import com.lucky_vicky.delivery_project.product.service.ProductService;
import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    //상품 추가 (store, admin)
    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductRequestDto productRequestDto) {

        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);

        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    //상품 수정(store, admin)
    @PutMapping("/{productsId}")
    public ResponseEntity updateProduct(@PathVariable UUID productsId, @RequestBody ProductRequestDto productRequestDto) {

        ProductResponseDto productResponseDto = productService.updateProduct(productsId, productRequestDto);

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    //상품 목록 조회 (public) -> 생성일, 수정일 순 정렬
    @GetMapping
    public ResponseEntity getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort) {

        CustomPageResponse<ProductResponseDto> products = productService.getProducts(page-1,size,sort);
        return ResponseEntity.ok(products);
    }

    //상품 상세 조회 (public)

    //상품 검색 (public)
    //상품 삭제 (store, admin)

}
