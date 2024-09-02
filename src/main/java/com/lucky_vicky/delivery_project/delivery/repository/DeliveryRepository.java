package com.lucky_vicky.delivery_project.delivery.repository;

import com.lucky_vicky.delivery_project.delivery.domain.Delivery;
import com.lucky_vicky.delivery_project.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    boolean existsByAddress(String address);

    Optional<Delivery> findByUser(User user);

    List<Delivery> findAllByUser(User user);

    Page<Delivery> findAllByUser(User user, Pageable pageable);
}
