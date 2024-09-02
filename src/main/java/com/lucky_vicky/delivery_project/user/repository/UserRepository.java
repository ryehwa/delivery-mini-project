package com.lucky_vicky.delivery_project.user.repository;

import com.lucky_vicky.delivery_project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    // username 중복 체크를 위한 메서드
    boolean existsByUsername(String username);

    // 이메일을 통한 사용자 조회 (이메일 필드가 User 엔티티에 있다고 가정)
//    Optional<User> findByEmail(String email);
//
//    // 이메일 중복 체크를 위한 메서드
//    boolean existsByEmail(String email);

    // 전화번호를 통한 사용자 조회
    Optional<User> findByPhoneNumber(String phoneNumber);

    // 전화번호 중복 체크를 위한 메서드
    boolean existsByPhoneNumber(String phoneNumber);
}