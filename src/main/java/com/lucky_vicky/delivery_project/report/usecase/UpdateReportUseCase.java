package com.lucky_vicky.delivery_project.report.usecase;

import com.lucky_vicky.delivery_project.global.annotation.UseCase;
import com.lucky_vicky.delivery_project.report.dto.UpdateReportRequestDto;

import java.util.UUID;

@UseCase("updateReportUseCase")
public interface UpdateReportUseCase {
    void updateReport(UUID userId, UpdateReportRequestDto updateReportRequestDto);
}
