package com.lucky_vicky.delivery_project.order.domain.entity;

import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
import com.lucky_vicky.delivery_project.order.domain.enums.OrderStatusEnum;
import com.lucky_vicky.delivery_project.review.domain.entity.Review;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "p_order")
public class Order extends AuditingEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDelivery> orderDeliveryList = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum status;

    @Column(name = "is_online", nullable = false)
    private boolean isOnline;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Review review;

    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }
}
