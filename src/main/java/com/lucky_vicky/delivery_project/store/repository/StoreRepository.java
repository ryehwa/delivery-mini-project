package com.lucky_vicky.delivery_project.store.repository;

import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

    @EntityGraph(attributePaths = {"storeCategoryMappers.storeCategory"})
    @Query("SELECT s FROM Store s WHERE s.isHidden = false AND s.isDeleted = false")
    Page<Store> findAllByIsHiddeFalseAndIsDeletedFalse(Pageable pageable);

    @Query("SELECT s FROM Store s WHERE s.isHidden = false AND s.isDeleted = false AND (s.name LIKE %:text%)")
    Page<Store> findByIsHiddenFalseAndIsDeletedFalseAndNameContaining(@Param("text") String text, Pageable pageable);

    @Query("SELECT s FROM Store s WHERE s.user = :user")
    Optional<Store> findByUser(User user);

    @Query("SELECT s FROM Store s WHERE s.id = :id AND s.isDeleted = false")
    Optional<Store> findByIdAnAndIsDeletedFalse(UUID id);


    @Query("SELECT s, COALESCE(AVG(r.rate), 0) AS averageRate " +
            "FROM Store s " +
            "LEFT JOIN FETCH s.storeCategoryMappers scm " +
            "LEFT JOIN FETCH scm.storeCategory sc " +
            "LEFT JOIN Order o ON o.store.id = s.id " +
            "LEFT JOIN Review r ON r.order.id = o.id " +
            "WHERE s.isHidden = false AND s.isDeleted = false " +
            "GROUP BY s.id, scm.id, sc.id")
    Page<Object[]> findStoresWithAverageRate(Pageable pageable);

}
