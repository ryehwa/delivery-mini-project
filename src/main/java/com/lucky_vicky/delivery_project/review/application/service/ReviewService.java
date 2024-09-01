package com.lucky_vicky.delivery_project.review.application.service;

import com.lucky_vicky.delivery_project.review.application.dto.ReviewRequestDTO;

public interface ReviewService {

    // 리뷰 생성
    void createReview(ReviewRequestDTO reviewRequestDTO);
}
