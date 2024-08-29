package com.lucky_vicky.delivery_project.payment.application.service;

import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.repository.OrderRepository;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentListDTO;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentRequestDTO;
import com.lucky_vicky.delivery_project.payment.application.dto.PaymentResponseDTO;
import com.lucky_vicky.delivery_project.payment.domain.entity.Payment;
import com.lucky_vicky.delivery_project.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * 결제 목록 조회
     *
     * @param userId
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @return Page<PaymentListDTO>
     */
    @Override
    public Page<PaymentListDTO> getPaymentList(Long userId, int page, int size, String sortBy, boolean desc) {

        // 정렬 방향
        Sort.Direction direction = desc ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Payment> paymentPage = paymentRepository.findAllByUserId(userId, pageable);

        return paymentPage.map(PaymentListDTO::toDTO);
    }


    /**
     * 결제 내역 생성
     *
     * @param paymentRequestDTO
     * @return PaymentResponseDTO
     */
    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {

        User user = userRepository.findById(paymentRequestDTO.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Order order = orderRepository.findById(paymentRequestDTO.getOrderId()).orElseThrow(
                () -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        Payment payment = Payment.builder()
                .user(user)
                .order(order)
                .totalPrice(order.getTotalPrice())
                .status(paymentRequestDTO.getStatus())
                .paymentMethod(paymentRequestDTO.getPaymentMethod())
                .build();

        paymentRepository.save(payment);

        return PaymentResponseDTO.toDTO(payment);
    }

    /**
     * 주문 내역 조회
     *
     * @param paymentId
     * @return
     */
    @Override
    public PaymentResponseDTO findByPaymentId(UUID paymentId) {

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new IllegalArgumentException("결제 내역이 존재하지 않습니다."));

        return PaymentResponseDTO.toDTO(payment);
    }
}
