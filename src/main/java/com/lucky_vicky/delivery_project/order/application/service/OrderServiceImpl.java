package com.lucky_vicky.delivery_project.order.application.service;

import com.lucky_vicky.delivery_project.delivery.domain.Delivery;
import com.lucky_vicky.delivery_project.delivery.repository.DeliveryRepository;
import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.order.application.dto.OrderListDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderRequestDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderResponseDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderUpdateStatusDTO;
import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.order.domain.entity.OrderDelivery;
import com.lucky_vicky.delivery_project.order.domain.entity.OrderProduct;
import com.lucky_vicky.delivery_project.order.domain.enums.OrderStatusEnum;
import com.lucky_vicky.delivery_project.order.domain.repository.OrderRepository;
import com.lucky_vicky.delivery_project.product.domain.Product;
import com.lucky_vicky.delivery_project.product.repository.ProductRepository;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.store.repository.StoreRepository;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
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
    public Page<OrderListDTO> getOrderByUserId(UUID userId, int page, int size, String sortBy, boolean orderBy) {

        // 사이즈 10,30,50 이외의 값이 들어왔을 때 값 고정
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        // 정렬 방향
        Sort.Direction direction = orderBy ? Sort.Direction.DESC : Sort.Direction.ASC;

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
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(orderRequestDTO.getStoreId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        // order 객체 생성
        Order order = Order.builder()
                .user(user)
                .store(store)
                .isOnline(orderRequestDTO.isOnline())
                .status(OrderStatusEnum.PENDING)
                .build();

        // OrderProduct 리스트 생성
        List<OrderProduct> orderProductList = orderRequestDTO.getOrderProductList().stream().map(dto -> {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUNT));

            return OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .amount(dto.getAmount())
                    .build();
        }).toList();

        // order에 orderProductList 주입
        order.setOrderProductList(orderProductList);

        // totalPrice 계산
        int totalPrice = order.getOrderProductList().stream().mapToInt(p -> p.getProduct().getPrice() * p.getAmount()).sum();

        // order에 totalPrice 주입
        order.setTotalPrice(totalPrice);

        // 기본 배송지 설정 default true 일 때
        if (orderRequestDTO.isDefault()) {
            // 사용자 ID와 기본 배송지 여부를 기반으로 배송지 조회
            Delivery delivery = deliveryRepository.findByUserIdAndIsDefault(
                    orderRequestDTO.getUserId(), true);

            if (delivery == null) {
                throw new BusinessLogicException(ExceptionCode.DEFAULT_DELIVERY_NOT_FOUND);
            }

            // OrderDelivery 리스트 생성
            OrderDelivery orderDelivery = OrderDelivery.builder()
                    .order(order)
                    .delivery(delivery)
                    .build();

            // order에 orderDelivery 주입
            order.setOrderDeliveryList(Arrays.asList(orderDelivery));

        } else {
            // Delivery 객체 생성
            Delivery delivery = Delivery.builder()
                    .user(user)
                    .address(orderRequestDTO.getAddress())
                    .recipientName(orderRequestDTO.getRecipientName())
                    .isDefault(orderRequestDTO.isDefaultCheck())
                    .build();

            delivery = deliveryRepository.save(delivery);

            // OrderDelivery 리스트 생성
            OrderDelivery orderDelivery = OrderDelivery.builder()
                    .order(order)
                    .delivery(delivery)
                    .build();

            // order에 orderDelivery 주입
            order.setOrderDeliveryList(Arrays.asList(orderDelivery));
        }

        orderRepository.save(order);

        return OrderResponseDTO.toResponseDTO(order);
    }

    /**
     * orderId로 주문 상세 조회
     *
     * @param orderId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public OrderResponseDTO findOrderById(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUNT));

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
                () -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUNT));

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
                () -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUNT));

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 주문 생성 시 시간
        LocalDateTime createdAt = order.getCreatedAt();

        Duration duration = Duration.between(createdAt, now);

        if (duration.toMinutes() > CANCEL_MINUTE) {
            throw new BusinessLogicException(ExceptionCode.ORDER_CANCEL_TIME_EXCEEDED);
        }

        order.setStatus(OrderStatusEnum.CANCELED);
        orderRepository.save(order);

    }
}
