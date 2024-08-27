package com.lucky_vicky.delivery_project.product;

import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDto createProducts(@RequestBody ProductRequestDto productRequestDto) {
        // service : 권한 체크
        // service : 중복 확인 및 repository 저장
        return productService.createProduct(productRequestDto);
    }

}
