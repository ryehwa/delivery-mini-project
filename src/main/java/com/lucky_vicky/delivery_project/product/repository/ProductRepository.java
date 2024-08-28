package com.lucky_vicky.delivery_project.product.repository;

import com.lucky_vicky.delivery_project.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);

    // 상품 이름 일부가 일치하는 경우 검색
    Page<Product> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
