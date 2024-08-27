package com.lucky_vicky.delivery_project.product;

import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductRequestDto productRequestDto) {

        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);

        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{productsId}")
    public ResponseEntity updateProduct(@PathVariable UUID productsId, @RequestBody ProductRequestDto productRequestDto) {

        ProductResponseDto productResponseDto = productService.updateProduct(productsId, productRequestDto);

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public Page<ProductResponseDto> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort) {
        return productService.getProducts(page-1,size,sort);
    }


}
