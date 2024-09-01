package com.lucky_vicky.delivery_project.review.domain.repository;

import com.lucky_vicky.delivery_project.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
