package com.lucky_vicky.delivery_project.notice.dto;

import jakarta.validation.constraints.NotNull;

public record NoticeUpdateRequestDto(
        @NotNull String title,
        @NotNull String content
) {
}
