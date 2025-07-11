package com.abdoatiia542.GraduationProject.repository;


import com.abdoatiia542.GraduationProject.model.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationRepository
        extends JpaRepository<UserNotification, Long> {

    Page<UserNotification> findAllByUser_IdOrderByNotification_CreatedAtDesc(Long userId, Pageable pageable);

    Page<UserNotification> findAllByUser_IdAndReadIsFalse(Long userId, Pageable pageable);

    Optional<UserNotification> findByUserIdAndNotificationId(Long userId, Long notificationId);

    Integer countByUser_IdAndReadIsFalse(Long userId);

    List<UserNotification> findAllByUser_IdAndReadIsFalse(Long userId);
}