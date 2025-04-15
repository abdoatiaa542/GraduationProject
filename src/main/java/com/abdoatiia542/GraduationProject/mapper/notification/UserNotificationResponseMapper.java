package com.abdoatiia542.GraduationProject.mapper.notification;

import com.abdoatiia542.GraduationProject.dto.notification.UserNotificationResponse;
import com.abdoatiia542.GraduationProject.model.Notification;
import com.abdoatiia542.GraduationProject.model.UserNotification;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserNotificationResponseMapper implements Function<UserNotification, UserNotificationResponse> {
    @Override
    public UserNotificationResponse apply(UserNotification userNotification) {
        Notification notification = userNotification.getNotification();

        return new UserNotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getDescription(),
                notification.getCreatedAt(),
                userNotification.isRead()
        );
    }
}