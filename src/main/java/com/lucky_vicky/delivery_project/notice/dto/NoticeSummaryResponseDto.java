package com.lucky_vicky.delivery_project.notice.dto;

import com.lucky_vicky.delivery_project.notice.domain.Notice;
import lombok.Builder;

import java.util.UUID;

@Builder
public record NoticeSummaryResponseDto(
        UUID noticeId,
        String title,
        String authorName
) {
    public static NoticeSummaryResponseDto fromEntityPage(Notice notice) {
        return NoticeSummaryResponseDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .authorName(notice.getUser().getNickname())
                .build();
    }
}
