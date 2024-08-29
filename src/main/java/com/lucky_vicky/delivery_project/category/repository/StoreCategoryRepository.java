package com.lucky_vicky.delivery_project.category.repository;

import com.lucky_vicky.delivery_project.category.domain.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreCategoryRepository extends JpaRepository<StoreCategory, UUID> {
}
