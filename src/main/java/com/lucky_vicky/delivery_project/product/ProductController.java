package com.lucky_vicky.delivery_project.product;

import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        // service : 권한 체크
        // service : 중복 확인 및 repository 저장
        return productService.createProduct(productRequestDto);
    }

    @PutMapping("/{productsId}")
    public ProductResponseDto updateProduct(@PathVariable UUID productsId, @RequestBody ProductRequestDto productResponseDto) {
        return productService.updateProduct(productsId, productResponseDto);
    }

}
