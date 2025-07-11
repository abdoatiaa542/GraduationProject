package com.abdoatiia542.GraduationProject.service.notification;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.api.PageResponse;
import com.abdoatiia542.GraduationProject.dto.notification.UserNotificationResponse;
import com.abdoatiia542.GraduationProject.mapper.notification.UserNotificationResponseMapper;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.model.UserNotification;
import com.abdoatiia542.GraduationProject.repository.UserNotificationRepository;
import com.abdoatiia542.GraduationProject.service.db.DatabaseService;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import com.abdoatiia542.GraduationProject.utils.notification.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNotificationService implements IUserNotificationService {
    private final UserNotificationResponseMapper userNotificationResponseMapper;
    private final UserNotificationRepository userNotificationRepository;
    private final NotificationUtils notificationUtils;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Object getNotification(Long id) {
        User user = ContextHolderUtils.getUser();

        UserNotification userNotification = DatabaseService.get(userNotificationRepository::findByUserIdAndNotificationId,
                user.getId(), id, UserNotification.class);

        UserNotificationResponse response = userNotificationResponseMapper.apply(userNotification);

        return ApiResponse.of("Notification retrieved successfully", response);
    }

    @Override
    public Object readNotification(Long id) {
        User user = ContextHolderUtils.getUser();

        UserNotification userNotification = DatabaseService.get(userNotificationRepository::findByUserIdAndNotificationId,
                user.getId(), id, UserNotification.class);
        userNotification.setRead(true);

        userNotificationRepository.save(userNotification);

        Integer notificationsCount = notificationUtils.getUnreadNotificationsCount(user.getId());
        simpMessagingTemplate.convertAndSendToUser(user.getId().toString(), "/notifications-count", notificationsCount);

        UserNotificationResponse response = userNotificationResponseMapper.apply(userNotification);

        return ApiResponse.of("Notification read successfully", response);
    }

    @Override
    public Object deleteNotification(Long id) {
        User user = ContextHolderUtils.getUser();

        UserNotification userNotification = DatabaseService.get(userNotificationRepository::findByUserIdAndNotificationId,
                user.getId(), id, UserNotification.class);
        userNotificationRepository.delete(userNotification);

        Integer notificationsCount = notificationUtils.getUnreadNotificationsCount(user.getId());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(user.getId()), "/notifications-count", notificationsCount);

        return ApiResponse.of("Notification deleted successfully");
    }

    @Override
    public Object getAllNotifications(int pageSize, int pageNumber) {
        User user = ContextHolderUtils.getUser();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<UserNotification> userNotifications = userNotificationRepository
                .findAllByUser_IdOrderByNotification_CreatedAtDesc(user.getId(), pageable);

        Page<UserNotificationResponse> pageResponse = userNotifications
                .map(userNotificationResponseMapper);
        PageResponse<UserNotificationResponse> response = PageResponse.of(pageResponse);

        return ApiResponse.of("Notifications retrieved successfully", response);
    }

    @Override
    public Object readAllNotifications() {
        User user = ContextHolderUtils.getUser();

        List<UserNotification> userNotifications = userNotificationRepository
                .findAllByUser_IdAndReadIsFalse(user.getId());

        userNotifications.forEach(userNotification -> userNotification.setRead(true));

        userNotificationRepository.saveAll(userNotifications);

        return ApiResponse.of("All Notifications marked as read successfully");
    }
}