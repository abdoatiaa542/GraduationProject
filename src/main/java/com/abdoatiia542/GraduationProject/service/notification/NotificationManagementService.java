package com.abdoatiia542.GraduationProject.service.notification;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.saver.NotificationRequestSaver;
import com.abdoatiia542.GraduationProject.saver.UserNotificationSaver;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import com.abdoatiia542.GraduationProject.utils.notification.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationManagementService implements INotificationManagementService {

    private final UserRepository userRepository;
    private final UserNotificationSaver userNotificationSaver;
    private final NotificationRequestSaver notificationRequestSaver;
    private final SimpMessagingTemplate simpMessagingTemplate;
    //    private final NonStaticContextHolderUtils contextHolderUtils;
    private final NotificationUtils notificationUtils;


    @Override
    public Object getCountOfUnreadNotifications() {
        User user = ContextHolderUtils.getUser();
        return ApiResponse.of("Notifications count: ",
                notificationUtils.getUnreadNotificationsCount(user.getId()));
    }

}