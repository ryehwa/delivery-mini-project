package com.lucky_vicky.delivery_project.notice.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.notice.dto.NoticeCreateRequestDto;

import java.util.UUID;

@UseCase("createNoticeUseCase")
public interface CreateNoticeUseCase {
    void createNotice(UUID userId, NoticeCreateRequestDto requestDto);
}
