package com.lucky_vicky.delivery_project.store.repository;

import com.lucky_vicky.delivery_project.store.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

    @Query("SELECT s FROM Store s WHERE s.isHidden = false")
    Page<Store> findAllByIsHiddeFalse(Pageable pageable);

    @Query("SELECT s FROM Store s WHERE s.isHidden = false AND (s.name LIKE %:text%)")
    Page<Store> findByIsHiddenFalseAndNameContaining(@Param("text") String text, Pageable pageable);
}
