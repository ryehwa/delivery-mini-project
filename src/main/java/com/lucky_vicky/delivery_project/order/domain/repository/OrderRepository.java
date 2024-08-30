package com.lucky_vicky.delivery_project.order.domain.repository;

import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

   // Page<Order> findAllByUserId(Long userId, Pageable pageable);
}
