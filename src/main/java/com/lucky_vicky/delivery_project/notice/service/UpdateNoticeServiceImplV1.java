package com.lucky_vicky.delivery_project.notice.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.notice.domain.Notice;
import com.lucky_vicky.delivery_project.notice.dto.NoticeUpdateRequestDto;
import com.lucky_vicky.delivery_project.notice.repository.NoticeRepository;
import com.lucky_vicky.delivery_project.notice.usecase.UpdateNoticeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNoticeServiceImplV1 implements UpdateNoticeUseCase {

    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void updateNotice(UUID noticeId, NoticeUpdateRequestDto requestDto) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND));
        notice.update(requestDto.title(), requestDto.content());
    }
}
