package com.abdoatiia542.GraduationProject.service.notification;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.notification.CreateNotificationByRoleRequest;
import com.abdoatiia542.GraduationProject.mapper.notification.UserNotificationResponseMapper;
import com.abdoatiia542.GraduationProject.model.Notification;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.saver.FirebaseNotificationSender;
import com.abdoatiia542.GraduationProject.saver.NotificationRequestSaver;
import com.abdoatiia542.GraduationProject.saver.UserNotificationSaver;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import com.abdoatiia542.GraduationProject.utils.notification.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class NotificationManagementService implements INotificationManagementService {

    private final UserRepository userRepository;
    private final UserNotificationSaver userNotificationSaver;
    private final NotificationRequestSaver notificationRequestSaver;
    private final SimpMessagingTemplate simpMessagingTemplate;
    //    private final NonStaticContextHolderUtils contextHolderUtils;
    private final NotificationUtils notificationUtils;
    private final UserNotificationResponseMapper userNotificationResponseMapper;
    private final FirebaseNotificationSender firebaseNotificationSender;


    @Override
    public Object getCountOfUnreadNotifications() {
        User user = ContextHolderUtils.getUser();
        return ApiResponse.of("Notifications count: ",
                notificationUtils.getUnreadNotificationsCount(user.getId()));
    }

    @Override
    public ApiResponse createNotification(CreateNotificationByRoleRequest request) {
        // 1. أنشئ notification object واحفظه
        Notification savedNotification = notificationRequestSaver.apply(request);

        // 2. هات المستخدمين اللي ليهم الـ Role المطلوب
        List<User> users = userRepository.findAllByRoleIn(request.roles());

        if (users.isEmpty()) {
            return ApiResponse.of("No users found for roles: " + request.roles());
        }

        // 3. اربط الـ notification دي بكل user (علشان تحتفظ بيها في الـ history)
        userNotificationSaver.apply(savedNotification, users);

        // 4. ابعت Push Notification للموبايل بس باستخدام FCM
        notificationUtils.sendNotificationToDeviceTokens(users, savedNotification);

        return ApiResponse.success("Notification created and sent to mobile users.");
    }


}