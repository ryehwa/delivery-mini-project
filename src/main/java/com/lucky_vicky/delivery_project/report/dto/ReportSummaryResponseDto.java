package com.lucky_vicky.delivery_project.report.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReportSummaryResponseDto(
        UUID reportId,
        UUID targetId,
        String targetType,
        String title,
        String status,
        String reporterName
) {
}
