package com.lucky_vicky.delivery_project.review.application.dto;

import com.lucky_vicky.delivery_project.review.domain.entity.Review;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReviewResponseDTO {

    private UUID reviewId;
    private UUID storeId;
    private UUID orderId;
    private String review;
    private int rate;

    public static ReviewResponseDTO toDTO (Review review){
        return ReviewResponseDTO.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .orderId(review.getOrder().getId())
                .review(review.getReview())
                .rate(review.getRate())
                .build();
    }

}
