package com.lucky_vicky.delivery_project.review.application.service;

import com.lucky_vicky.delivery_project.review.application.dto.ReviewRequestDTO;
import com.lucky_vicky.delivery_project.review.application.dto.ReviewResponseDTO;

import java.util.UUID;

public interface ReviewService {

    // 가게 후기 생성
    void createReview(ReviewRequestDTO reviewRequestDTO);
    
    // 가게 후기 조회
    ReviewResponseDTO findReviewById(UUID reviewId);
}
