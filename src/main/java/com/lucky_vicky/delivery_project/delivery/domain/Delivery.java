package com.lucky_vicky.delivery_project.delivery.domain;

import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import com.lucky_vicky.delivery_project.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_delivery")
public class Delivery extends AuditingEntity {

    @Id
    private UUID id;
    private String address;
    private String recipientName;
    private boolean isDefault;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //UUID 자동생성
    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }

    public Delivery(DeliveryRequestDto deliveryRequestDto) {
        this.address = deliveryRequestDto.getAddress();
        this.recipientName = deliveryRequestDto.getRecipientName();
        this.isDefault = deliveryRequestDto.isDefault();
    }


    public void update(DeliveryRequestDto deliveryRequestDto) {
        this.address = deliveryRequestDto.getAddress();
        this.recipientName = deliveryRequestDto.getRecipientName();
        this.isDefault = deliveryRequestDto.isDefault();
    }

    public void saveDefaultAddress() {
        this.isDefault = true;
    }

    public void checkUser(User user) {
        if (!user.equals(user)) {
            throw new IllegalArgumentException("잘못된 배송 아이디 입니다.");
        }
    }
}
