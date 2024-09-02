package com.lucky_vicky.delivery_project.report.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateReportRequestDto(
        @NotNull UUID targetId,
        @NotNull String targetType,
        @NotNull String title,
        @NotNull String content
) {
}
