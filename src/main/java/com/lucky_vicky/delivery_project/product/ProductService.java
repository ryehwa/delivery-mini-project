package com.lucky_vicky.delivery_project.product;

import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        // 권한 체크

        //entity 객체로 만들고 db 저장
        Product product = productRepository.save(new Product(productRequestDto));
        return new ProductResponseDto(product);
    }
}
