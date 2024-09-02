package com.lucky_vicky.delivery_project.notice.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.notice.dto.NoticeDetailResponseDto;
import com.lucky_vicky.delivery_project.notice.dto.NoticeSummaryResponseDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

@UseCase("readNoticeUseCase")
public interface ReadNoticeUseCase {
    NoticeDetailResponseDto readNoticeDetail(UUID noticeId);
    Page<NoticeSummaryResponseDto> readNoticeSummaryList(int page, int size, String sortBy, String orderBy);
    Page<NoticeSummaryResponseDto> searchNoticeSummaryList(int page, int size, String keyword);
}
