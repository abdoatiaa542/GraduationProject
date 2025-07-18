package com.abdoatiia542.GraduationProject.repository;

import com.abdoatiia542.GraduationProject.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}