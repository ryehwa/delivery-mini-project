package com.lucky_vicky.delivery_project.delivery.repository;

import com.lucky_vicky.delivery_project.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    boolean existsByAddress(String address);

    Delivery findByUserIdAndDefault(UUID userId, boolean isDefault);
}
