package com.abdoatiia542.GraduationProject.model;

import com.abdoatiia542.GraduationProject.model.enumerations.NotificationType;
import com.abdoatiia542.GraduationProject.utils.base.FirebaseNotificationTemplate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTIFICATIONS")
public class Notification implements FirebaseNotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, columnDefinition = "enum('NOTIFICATION', 'MESSAGE') NOT NULL DEFAULT 'NOTIFICATION'")
    private NotificationType type;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "notification", fetch = FetchType.EAGER)
    private Set<UserNotification> userNotifications;

    @Override
    public String getBody() {
        return this.description;
    }
}