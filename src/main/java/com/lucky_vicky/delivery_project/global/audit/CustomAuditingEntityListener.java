package com.lucky_vicky.delivery_project.global.audit;

import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class CustomAuditingEntityListener {
    private final AuditorAware<String> auditorAware;

    @Autowired
    public CustomAuditingEntityListener(AuditorAware<String> auditorAware) {
        this.auditorAware = auditorAware;
    }

    @PreRemove
    public void setDeletedInfo(AuditingEntity entity) {
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsDeleted(true);
        entity.setDeletedBy(auditorAware.getCurrentAuditor().orElse("Unknown"));
    }
}
