package com.lucky_vicky.delivery_project.review.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewReportDTO {

    private UUID reviewId;
    private String report;
}
