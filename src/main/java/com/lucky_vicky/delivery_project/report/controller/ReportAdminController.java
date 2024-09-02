package com.lucky_vicky.delivery_project.report.controller;

import com.lucky_vicky.delivery_project.global.annotation.UserId;
import com.lucky_vicky.delivery_project.report.dto.CreateReplyRequestDto;
import com.lucky_vicky.delivery_project.report.usecase.CreateReportUseCase;
import com.lucky_vicky.delivery_project.report.usecase.DeleteReportUseCase;
import com.lucky_vicky.delivery_project.report.usecase.ReadReportUseCase;
import com.lucky_vicky.delivery_project.report.usecase.UpdateReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports/admin")
@RequiredArgsConstructor
public class ReportAdminController {
    private final CreateReportUseCase createReportUseCase;
    private final DeleteReportUseCase deleteReportUseCase;
    private final ReadReportUseCase readReportUseCase;
    private final UpdateReportUseCase updateReportUseCase;


    /**
     * 모든 신고 내역 조회
     * ADMIN
     */
    @GetMapping("")
    public ResponseEntity<?> readReportList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = "DESC") String orderBy

    ) {
        return ResponseEntity.ok(readReportUseCase.readAllReports(page, size, sortBy, orderBy));
    }

    /**
     * 신고 상세 조회
     * ADMIN
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<?> readReportDetail(
            @UserId UUID userId,
            @PathVariable UUID reportId
    ) {
        return ResponseEntity.ok(readReportUseCase.readReport(userId, reportId));
    }

    /**
     * 신고 검색
     * ADMIN
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchReportList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "text") String text
    ) {
        return ResponseEntity.ok(readReportUseCase.searchReports(page, size, text));
    }

    /**
     * 신고 답변 등록
     * ADMIN
     */
    @PostMapping("/{reportId}")
    public ResponseEntity<?> createReportAnswer(
            @PathVariable UUID reportId,
            @RequestBody CreateReplyRequestDto requestDto
    ) {
        createReportUseCase.replyReport(reportId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 신고 삭제
     * ADMIN
     */
    @DeleteMapping("/{reportId}")
    public ResponseEntity<?> deleteReport(
            @PathVariable UUID reportId
    ) {
        deleteReportUseCase.deleteReportByAdmin(reportId);
        return ResponseEntity.ok().build();
    }
}
