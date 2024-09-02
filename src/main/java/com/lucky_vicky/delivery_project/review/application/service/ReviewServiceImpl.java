package com.lucky_vicky.delivery_project.review.application.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.repository.OrderRepository;
import com.lucky_vicky.delivery_project.review.application.dto.ReviewListDTO;
import com.lucky_vicky.delivery_project.review.application.dto.ReviewRequestDTO;
import com.lucky_vicky.delivery_project.review.application.dto.ReviewResponseDTO;
import com.lucky_vicky.delivery_project.review.application.dto.ReviewUpdateDTO;
import com.lucky_vicky.delivery_project.review.domain.entity.Review;
import com.lucky_vicky.delivery_project.review.domain.repository.ReviewRepository;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    /**
     * 가게 후기 작성
     *
     * @param reviewRequestDTO
     */
    @Transactional
    @Override
    public void createReview(ReviewRequestDTO reviewRequestDTO) {

        User user = userRepository.findById(reviewRequestDTO.getUserId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(reviewRequestDTO.getStoreId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        Order order = orderRepository.findById(reviewRequestDTO.getOrderId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUNT));

        Review review = Review.builder()
                .review(reviewRequestDTO.getReview())
                .rate(reviewRequestDTO.getRate())
                .user(user)
                .store(store)
                .order(order)
                .build();

        reviewRepository.save(review);
    }

    /**
     * 가게 후기 조회
     *
     * @param reviewId
     * @return
     */
    @Override
    public ReviewResponseDTO findReviewById(UUID reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));

        return ReviewResponseDTO.toDTO(review);
    }

    /**
     * 가게 후기 수정
     *
     * @param reviewUpdateDTO
     * @return
     */
    @Transactional
    @Override
    public ReviewResponseDTO updateReview(ReviewUpdateDTO reviewUpdateDTO) {

        Review review = reviewRepository.findById(reviewUpdateDTO.getReviewId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));

        review.setReview(reviewUpdateDTO.getReview());
        review.setRate(reviewUpdateDTO.getRate());

        reviewRepository.save(review);

        return ReviewResponseDTO.toDTO(review);
    }

    /**
     * 가게 후기 삭제
     *
     * @param reviewId
     */
    @Transactional
    @Override
    public void deleteReview(UUID reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));

        review.setIsDeleted(true);
        reviewRepository.save(review);
    }

    /**
     * 가게 후기 목록 조회
     * @param storeId
     * @param page
     * @param size
     * @param sortBy
     * @param orderBy
     * @return
     */
    @Override
    public Page<ReviewListDTO> getReviewsByStore(UUID storeId, int page, int size, String sortBy, boolean orderBy) {

        // 사이즈 10,30,50 이외의 값이 들어왔을 때 값 고정
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = orderBy ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Review> reviewPage = reviewRepository.findAllByStoreIdAndIsDeletedFalse(storeId, pageable);

        return reviewPage.map(ReviewListDTO::toDTO);
    }
}
