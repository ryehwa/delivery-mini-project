package com.lucky_vicky.delivery_project.store.domain;

import com.lucky_vicky.delivery_project.category.domain.StoreCategoryMapper;
import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
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

    /* -------------- Mapping -------------- */
    // order와 매핑 필요
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategoryMapper> storeCategoryMappers = new ArrayList<>();

    /* -------------- Constructor -------------- */
    @Builder
    public Store(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }

    /* -------------- Methods -------------- */
    public void update(String name, String address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }
}
