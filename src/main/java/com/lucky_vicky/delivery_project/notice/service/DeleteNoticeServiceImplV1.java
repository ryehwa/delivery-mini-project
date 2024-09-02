package com.lucky_vicky.delivery_project.notice.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.notice.domain.Notice;
import com.lucky_vicky.delivery_project.notice.repository.NoticeRepository;
import com.lucky_vicky.delivery_project.notice.usecase.DeleteNoticeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteNoticeServiceImplV1 implements DeleteNoticeUseCase {

    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void deleteNotice(UUID noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND));
        notice.delete();
    }
}
