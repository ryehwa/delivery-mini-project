package com.lucky_vicky.delivery_project.notice.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.notice.dto.NoticeUpdateRequestDto;

import java.util.UUID;

@UseCase("updateNoticeUseCase")
public interface UpdateNoticeUseCase {
    void updateNotice(UUID noticeId, NoticeUpdateRequestDto requestDto);
}
