package com.lucky_vicky.delivery_project.report.dto;

import java.util.UUID;

public record CreateReportRequestDto(
        UUID targetId,
        String targetType,
        String title,
        String content
) {
}
