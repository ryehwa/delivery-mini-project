package com.lucky_vicky.delivery_project.notice.repository;

import com.lucky_vicky.delivery_project.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {
    @Query("SELECT n FROM Notice n WHERE n.isDeleted = false")
    Page<Notice> findAllByIsDeletedFalse(Pageable pageable);

    @Query("SELECT n FROM Notice n WHERE n.isDeleted = false AND (n.title LIKE %:text%)")
    Page<Notice> findByIsDeletedFalseAndTitleContaining(String text, Pageable pageable);
}
