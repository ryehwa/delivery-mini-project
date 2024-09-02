package com.lucky_vicky.delivery_project.store.domain;

import com.lucky_vicky.delivery_project.category.domain.StoreCategoryMapper;
import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
import com.lucky_vicky.delivery_project.product.domain.Product;
import com.lucky_vicky.delivery_project.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "p_store")
public class Store extends AuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;   // true: 숨김, false: 보임

    /* -------------- Mapping -------------- */
    // Product와 매핑
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    // 카테고리와 매핑
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategoryMapper> storeCategoryMappers = new ArrayList<>();

    // Store 권한을 가진 유저와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    /* -------------- Constructor -------------- */
    @Builder
    public Store(String name, String address, String number, User user) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.user = user;
        this.isHidden = true;
    }

    /* -------------- Methods -------------- */
    public void updateStoreInfo(String name, String address, String number, List<StoreCategoryMapper> storeCategoryMappers) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.storeCategoryMappers = storeCategoryMappers;
    }

    public void updateStoreStatus() {
        this.isHidden = false;
    }

    public void delete() {
        this.setIsDeleted(true);
    }
}
