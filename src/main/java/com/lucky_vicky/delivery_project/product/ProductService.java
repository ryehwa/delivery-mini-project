package com.lucky_vicky.delivery_project.product;

import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        // 권한 체크

        //entity 객체로 만들고 db 저장
        Product product = productRepository.save(new Product(productRequestDto));
        //Global Exception

        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto productRequestDto) {

        //권한 체크

        //update
        Product product = productRepository.findById(id).
                // Global Exception
                orElseThrow(() -> new NullPointerException("상품을 찾을 수 없습니다."));
        product.update(productRequestDto);
        return new ProductResponseDto(product);
    }
}
