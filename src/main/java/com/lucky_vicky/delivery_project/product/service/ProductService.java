package com.lucky_vicky.delivery_project.product;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        // 권한 체크

        // dto -> Entity
        Product product = new Product(productRequestDto);
        // 중복 제품 확인
        if(productRepository.existsByName(product.getName())) {
            throw new BusinessLogicException(ExceptionCode.EXIST_PRODUCT);
        }
        //entity 객체로 만들고 db 저장
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto productRequestDto) {
        //권한 체크

        //상품 존재 여부 확인
        Product product = productRepository.findById(id).
                orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUNT));
        product.update(productRequestDto);
        // db 저장
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    public Page<ProductResponseDto> getProducts(int page, int size, String sort) {
        //오름차순, 내림차순
        //Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<Product> productList = productRepository.findAll(pageable);

        return productList.map(ProductResponseDto:: new);
    }
}
