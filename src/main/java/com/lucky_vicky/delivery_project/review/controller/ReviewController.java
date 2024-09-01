package com.lucky_vicky.delivery_project.review.controller;

import com.lucky_vicky.delivery_project.review.application.dto.ReviewRequestDTO;
import com.lucky_vicky.delivery_project.review.application.service.ReviewService;
import com.lucky_vicky.delivery_project.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2(topic = "Review Controller")
@RequestMapping("/api/v1/stores")
public class ReviewController {

    private final ReviewService reviewService;
    
    // 가게 후기 작성
    @PostMapping("/{storeId}/reviews")
    public ResponseEntity<String> createReview(@PathVariable("storeId")UUID storeId, @AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ReviewRequestDTO reviewRequestDTO){

        log.info("Review Controller | POST Create Review");

        reviewRequestDTO.setStoreId(storeId);

        UUID userId = userPrincipal.getId();
        reviewRequestDTO.setUserId(userId);

        reviewService.createReview(reviewRequestDTO);

        return ResponseEntity.ok("후기 작성이 성공적으로 되었습니다.");

    }
}
