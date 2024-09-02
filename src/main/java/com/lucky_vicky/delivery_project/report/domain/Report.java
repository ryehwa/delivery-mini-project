package com.lucky_vicky.delivery_project.report.domain;

import com.lucky_vicky.delivery_project.global.audit.AuditingEntity;
import com.lucky_vicky.delivery_project.report.domain.type.EReportStatus;
import com.lucky_vicky.delivery_project.report.domain.type.EReportTargetType;
import com.lucky_vicky.delivery_project.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "p_report")
public class Report extends AuditingEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // 신고 대상 UUID
    @Column(name = "target_id", nullable = false)
    private UUID targetId;

    // 신고 대상 Type
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private EReportTargetType targetType;

    // 신고 제목
    @Column(name = "title", nullable = false)
    private String title;

    // 신고 내용
    @Column(name = "content", nullable = false)
    private String content;

    // 신고 처리 Status
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EReportStatus status;

    @Column(name = "reply")
    private String reply;

    /* -------------- Mapping -------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /* -------------- Constructor -------------- */
    @Builder
    public Report(String title, String content,
                  User user, UUID targetId,
                  EReportTargetType targetType) {
        this.title = title;
        this.content = content;
        this.targetId = targetId;
        this.user = user;
        this.targetType = targetType;
        this.status = EReportStatus.PENDING;
        this.reply = null;
    }

    /* -------------- Methods -------------- */
    public void update(String title, String content, EReportTargetType targetType, UUID targetId) {
        this.title = title;
        this.content = content;
        this.targetType = targetType;
        this.targetId = targetId;
    }

    public void delete() {
        this.setIsDeleted(true);
    }

    public void setReply(String reply) {
        this.reply = reply;
        this.status = EReportStatus.EXECUTED;
    }
}
