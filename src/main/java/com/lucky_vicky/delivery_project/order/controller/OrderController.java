package com.lucky_vicky.delivery_project.order.controller;

import com.lucky_vicky.delivery_project.order.application.dto.OrderListDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderRequestDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderResponseDTO;
import com.lucky_vicky.delivery_project.order.application.dto.OrderUpdateStatusDTO;
import com.lucky_vicky.delivery_project.order.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2(topic = "Order Controller")
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    // GET OrderList
    @GetMapping("/orders")
    public Page<OrderListDTO> getOrderList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createAt") String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") boolean orderBy,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        log.info("OrderController : GET getOrderList");



        Long userId = userDetails.getUser().getUserId();

        return orderService.getOrderByUserId(userId, page-1, size, sortBy, orderBy);

    }

    // Create Order
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        log.info("OrderController : POST createOrder");

        Long userId = userDetails.getUser().getUserId();
        orderRequestDTO.setUserId(userId);

        OrderResponseDTO dto = orderService.createOrder(orderRequestDTO);

        return ResponseEntity.ok(dto);
    }

    // FindByOrderId
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> findByOrderId(@PathVariable UUID orderId) {

        log.info("OrderController : GET findByOrderId");

        OrderResponseDTO dto = orderService.findOrderById(orderId);

        return ResponseEntity.ok(dto);

    }

    // Update Order Status
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderUpdateStatusDTO> updateOrderStatus(@PathVariable UUID orderId, @RequestBody OrderUpdateStatusDTO orderUpdateStatusDTO) {

        log.info("OrderController : PUT updateOrderStatus");

        orderUpdateStatusDTO.setOrderId(orderId);
        OrderUpdateStatusDTO dto = orderService.updateOrderStatus(orderUpdateStatusDTO);

        return ResponseEntity.ok(dto);
    }

    // Cancel Order
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId) {

        log.info("OrderController : DELETE cancelOrder");

        orderService.cancelOrder(orderId);

        return ResponseEntity.ok("주문이 취소되었습니다.");
    }
}
