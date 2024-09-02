package com.lucky_vicky.delivery_project.store.repository;

import com.lucky_vicky.delivery_project.category.domain.StoreCategoryMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreCategoryMapperRepository extends JpaRepository<StoreCategoryMapper, UUID> {
}
