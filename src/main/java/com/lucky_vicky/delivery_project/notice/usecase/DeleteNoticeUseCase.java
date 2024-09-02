package com.lucky_vicky.delivery_project.notice.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;

import java.util.UUID;

@UseCase("deleteNoticeUseCase")
public interface DeleteNoticeUseCase {
    void deleteNotice(UUID noticeId);
}
