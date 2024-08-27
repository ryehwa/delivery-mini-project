package com.lucky_vicky.delivery_project.order.application.service;

import com.lucky_vicky.delivery_project.order.application.dto.OrderListDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderRequestDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderResponseDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderUpdateStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

    // getList
    Page<OrderListDTO> getOrderByUserId(Long userId, int page, int size, String sortBy, boolean desc);

    // Create Order
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    // FindOrderById
    OrderResponseDTO findOrderById(UUID orderId);

    // Update Order Status
    OrderUpdateStatusDTO updateOrderStatus(OrderUpdateStatusDTO orderUpdateStatusDTO);

    // Cancel Order
    void cancelOrder(UUID orderId);
}
