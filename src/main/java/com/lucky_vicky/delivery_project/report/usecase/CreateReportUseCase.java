package com.lucky_vicky.delivery_project.report.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.report.dto.CreateReplyRequestDto;
import com.lucky_vicky.delivery_project.report.dto.CreateReportRequestDto;

import java.util.UUID;

@UseCase("createReportUseCase")
public interface CreateReportUseCase {
    void createReport(UUID userId, CreateReportRequestDto requestDto);
    void replyReport(UUID reportId, CreateReplyRequestDto requestDto);
}
