package com.lucky_vicky.delivery_project.review.domain.entity;

import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
import com.lucky_vicky.delivery_project.order.domain.entity.Order;
import com.lucky_vicky.delivery_project.store.domain.Store;
import com.lucky_vicky.delivery_project.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends AuditingEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "review", nullable = false)
    private String review;

    @Min(1)
    @Max(5)
    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "report")
    private String report;

    @Column(name = "is_reported")
    private boolean reportFlag = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }


}
