package com.abdoatiia542.GraduationProject.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_NOTIFICATIONS", uniqueConstraints = {
        @UniqueConstraint(name = "USER_NOTIFICATION_UNIQUE_CONSTRAINT",
                columnNames = {"notification_id", "user_id"})})
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Notification notification;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Column(name = "is_read", nullable = false)
    private boolean read;

}