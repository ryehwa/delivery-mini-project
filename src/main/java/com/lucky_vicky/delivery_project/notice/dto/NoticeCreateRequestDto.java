package com.lucky_vicky.delivery_project.notice.dto;

import jakarta.validation.constraints.NotNull;

public record NoticeCreateRequestDto(
        @NotNull String title,
        @NotNull String content
) {
}
