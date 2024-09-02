package com.lucky_vicky.delivery_project.review.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReviewUpdateDTO {

    private UUID reviewId;
    private String review;
    private int rate;

}
