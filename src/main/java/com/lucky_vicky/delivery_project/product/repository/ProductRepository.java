package com.lucky_vicky.delivery_project.product.repository;

import com.lucky_vicky.delivery_project.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);
}
