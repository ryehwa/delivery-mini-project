package com.lucky_vicky.delivery_project.notice.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.notice.domain.Notice;
import com.lucky_vicky.delivery_project.notice.dto.NoticeCreateRequestDto;
import com.lucky_vicky.delivery_project.notice.repository.NoticeRepository;
import com.lucky_vicky.delivery_project.notice.usecase.CreateNoticeUseCase;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateNoticeServiceImplV1 implements CreateNoticeUseCase {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createNotice(UUID userId, NoticeCreateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Notice newNotice = Notice.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .user(user)
                .build();
        noticeRepository.save(newNotice);
    }
}
