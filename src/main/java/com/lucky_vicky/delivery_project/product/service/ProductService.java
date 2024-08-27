package com.lucky_vicky.delivery_project.product.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.global.util.CustomPageResponse;
import com.lucky_vicky.delivery_project.product.domain.Product;
import com.lucky_vicky.delivery_project.product.dto.ProductRequestDto;
import com.lucky_vicky.delivery_project.product.dto.ProductResponseDto;
import com.lucky_vicky.delivery_project.product.repository.ProductRepository;
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
        Product product = findProductById(id);
        //update
        product.update(productRequestDto);
        // db 저장
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    public CustomPageResponse<ProductResponseDto> getProducts(int page, int size, String sort) {
        //오름차순, 내림차순
        //Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        // 내림차순 default로 정렬
        Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
        //페이지 요청 생성
        Pageable pageable = PageRequest.of(page, size, sortBy);
        //데이터 조회
        Page<Product> productList = productRepository.findAll(pageable);
        //Dto로 변환
        Page<ProductResponseDto> dtoPage = productList.map(ProductResponseDto:: new);
        return new CustomPageResponse<>(dtoPage);
    }

    public ProductResponseDto getProduct(UUID productId) {
        //상품 존재 여부 확인
        Product product = findProductById(productId);
        //dto로 반환
        return new ProductResponseDto(product);
    }

    public CustomPageResponse<ProductResponseDto> searchProducts(String name, int page, int size, String sort) {
        // 내림차순 default로 정렬
        Sort sortBy = Sort.by(Sort.Direction.DESC, sort);
        //페이지 요청 생성
        Pageable pageable = PageRequest.of(page, size, sortBy);
        //데이터 조회
        Page<Product> searchProductList = productRepository.findByNameContainingIgnoreCase(pageable, name);
        //dto로 변환
        Page<ProductResponseDto> searchProductsPage = searchProductList.map(ProductResponseDto ::new);
        return new CustomPageResponse<>(searchProductsPage);
    }

    //논리적 삭제 -> 숨김 처리
    public void deleteProduct(UUID id) {
        //권한 체크

        //상품 검색
        Product product = findProductById(id);
        //hidden 처리
        product.setHidden(true);
        //db 저장
        productRepository.save(product);
    }


    //상품 존재 여부 확인 메서드
    public Product findProductById(UUID id){
        Product product =  productRepository.findById(id).
                orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUNT));
        //상품 hidden 처리 여부 확인
        if(product.isHidden()){
            throw new BusinessLogicException(ExceptionCode.PRODUCT_IS_HIDDEN);
        }
        return product;
    }
}
