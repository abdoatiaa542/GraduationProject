package com.abdoatiia542.GraduationProject.saver;

import com.abdoatiia542.GraduationProject.dto.notification.NotificationRequestTemplate;
import com.abdoatiia542.GraduationProject.model.Notification;
import com.abdoatiia542.GraduationProject.model.enumerations.NotificationType;
import com.abdoatiia542.GraduationProject.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class NotificationRequestSaver
        implements Function<NotificationRequestTemplate, Notification> {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification apply(NotificationRequestTemplate request) {
        return notificationRepository.save(Notification
                .builder()
                .title(request.title().strip())
                .description(request.description().strip())
                .type(NotificationType.NOTIFICATION)
                .build());
    }

}