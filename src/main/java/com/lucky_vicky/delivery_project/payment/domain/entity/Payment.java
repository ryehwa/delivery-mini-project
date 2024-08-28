package com.lucky_vicky.delivery_project.payment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "status", nullable = false)
    private PaymentStatusEnum status;

    @Column(name = "payment_method", nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = true;

    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }
}
