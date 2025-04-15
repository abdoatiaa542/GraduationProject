package com.abdoatiia542.GraduationProject.dto.notification;

import java.time.LocalDateTime;

public record UserNotificationResponse(
        Long id,

        String title,

        String description,

        LocalDateTime timestamp,

        boolean read
) {
}