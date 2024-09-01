package com.lucky_vicky.delivery_project.review.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewResponseDTO {

    private UUID reviewId;
    private UUID storeId;
    private UUID orderId;
    private String review;
    private int rate;

}
