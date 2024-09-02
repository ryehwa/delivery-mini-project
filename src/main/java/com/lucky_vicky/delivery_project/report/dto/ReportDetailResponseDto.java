package com.lucky_vicky.delivery_project.report.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReportDetailResponseDto(
        UUID reportId,
        UUID targetId,
        String targetType,
        String title,
        String content,
        String status,
        String reporterName
) {
}
