package com.lucky_vicky.delivery_project.notice.dto;

import com.lucky_vicky.delivery_project.notice.domain.Notice;
import lombok.Builder;

import java.util.UUID;

@Builder
public record NoticeDetailResponseDto(
        UUID noticeId,
        String title,
        String content,
        String createdAt,
        String updatedAt,
        String authorName
) {
    public static NoticeDetailResponseDto fromEntity(Notice notice) {
        return NoticeDetailResponseDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt().toString())
                .updatedAt(notice.getUpdatedAt().toString())
                .authorName(notice.getUser().getNickname())
                .build();
    }
}
