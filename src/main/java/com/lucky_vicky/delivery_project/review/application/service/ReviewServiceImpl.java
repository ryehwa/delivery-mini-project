package com.lucky_vicky.delivery_project.review.application.service;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.repository.OrderRepository;
import com.lucky_vicky.delivery_project.review.application.dto.ReviewRequestDTO;
import com.lucky_vicky.delivery_project.review.domain.entity.Review;
import com.lucky_vicky.delivery_project.review.domain.repository.ReviewRepository;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    /**
     * 가게 후기 작성
     * @param reviewRequestDTO 
     */
    @Override
    public void createReview(ReviewRequestDTO reviewRequestDTO) {

        User user = userRepository.findById(reviewRequestDTO.getUserId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(reviewRequestDTO.getStoreId()).orElseThrow(
                ()-> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

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
}
