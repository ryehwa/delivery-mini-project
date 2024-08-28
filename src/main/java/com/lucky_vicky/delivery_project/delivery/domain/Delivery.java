package com.lucky_vicky.delivery_project.delivery.domain;

import com.lucky_vicky.delivery_project.delivery.dto.DeliveryRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_delivery")
public class Delivery {

    @Id
    private UUID id;
    private String address;
    private String recipientName;
    private boolean isDefault;
    private boolean isDeleted;

    //UUID 자동생성
    @PrePersist
    protected void createUUID(){
        if(id == null) id = UUID.randomUUID();
    }

    public Delivery(DeliveryRequestDto deliveryRequestDto) {
        this.address = deliveryRequestDto.getAddress();
        this.recipientName = deliveryRequestDto.getRecipientName();
        this.isDefault = deliveryRequestDto.isDefault();
        this.isDeleted = deliveryRequestDto.isDeleted();
    }


}
