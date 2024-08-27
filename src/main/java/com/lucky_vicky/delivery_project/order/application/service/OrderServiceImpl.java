package com.lucky_vicky.delivery_project.order.application.service;

import com.lucky_vicky.delivery_project.order.application.dto.OrderListDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderRequestDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderResponseDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderUpdateStatusDTO;
import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.entity.OrderDelivery;
import com.lucky_vicky.delivery_project.order.domain.entity.OrderProduct;
import com.lucky_vicky.delivery_project.order.domain.entity.OrderStatusEnum;
import com.lucky_vicky.delivery_project.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

    private static final long CANCEL_MINUTE = 5;


    /**
     * userId에 맞는 주문 목록 보기
     *
     * @param userId
     * @param page
     * @param size
     * @param sortBy
     * @return
     */
    @Override
    public Page<OrderListDTO> getOrderByUserId(Long userId, int page, int size, String sortBy, boolean desc) {

        // 정렬 방향
        Sort.Direction direction = desc ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Order> orderpage = orderRepository.findAllByUserId(userId, pageable);

        return orderpage.map(OrderListDTO::toListDTO);
    }

    /**
     * 주문 생성
     *
     * @param orderRequestDTO
     * @return
     */
    @Transactional
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        // DB에서 user, store 조회
        User user = userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Store store = storeRepository.findById(orderRequestDTO.getStoreId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 가게입니다."));

        // order 객체 생성
        Order order = Order.builder()
                .user(user)
                .store(store)
                .totalPrice(orderRequestDTO.getTotalPrice())
                .isOnline(orderRequestDTO.isOnline())
                .status(OrderStatusEnum.PENDING)
                .isDeleted(false)
                .build();

        // OrderProduct 리스트 생성
        List<OrderProduct> orderProductList = orderRequestDTO.getOrderProductList().stream().map(dto -> {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                    () -> new IllegalArgumentException("유효하지 않은 상품입니다."));

            return OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .amount(dto.getAmount())
                    .isDeleted(false)
                    .build();
        }).toList();

        // order에 orderProductList 주입
        order.setOrderProductList(orderProductList);

        // Delivery 객체 생성
        Delivery delivery = Delivery.builder()
                .user(user)
                .address(orderRequestDTO.getAddress())
                .receipientName(orderRequestDTO.getRecipientName())
                .isDefault(orderRequestDTO.isDefault())
                .build();

        delivery = deliveryRepository.save(delivery);

        // OrderDelivery 리스트 생성
        OrderDelivery orderDelivery = OrderDelivery.builder()
                .order(order)
                .delivery(delivery)
                .isDeleted(false)
                .build();

        // order에 orderDelivery 주입
        order.getOrderDeliveryList().add(orderDelivery);

        orderRepository.save(order);

        return OrderResponseDTO.toResponseDTO(order);
    }

    /**
     * orderId로 주문 상세 조회
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderResponseDTO findOrderById(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 주문입니다."));

        return OrderResponseDTO.toResponseDTO(order);
    }

    /**
     * 주문 처리, Status를 COMPLETED, CANCELED로 변경
     *
     * @param orderUpdateStatusDTO
     * @return
     */
    @Transactional
    @Override
    public OrderUpdateStatusDTO updateOrderStatus(OrderUpdateStatusDTO orderUpdateStatusDTO) {

        Order order = orderRepository.findById(orderUpdateStatusDTO.getOrderId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 주문입니다."));

        order.setStatus(orderUpdateStatusDTO.getStatus());

        return OrderUpdateStatusDTO.toStatusDTO(order);
    }

    /**
     * 주문 취소, Status를 CANCELED로 변경
     *
     * @param orderId
     * @return
     */
    @Transactional
    @Override
    public void cancelOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 주문입니다."));

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 주문 생성 시 시간
        LocalDateTime createdAt = order.getCreatedAt();

        Duration duration = Duration.between(createdAt, now);

        if (duration.toMinutes() > CANCEL_MINUTE) {
            throw new IllegalArgumentException("주문 취소는 주문 후 5분 이내에만 가능합니다.");
        }

        order.setStatus(OrderStatusEnum.CANCELLED);
        orderRepository.save(order);

    }
}
