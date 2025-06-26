//package com.abdoatiia542.GraduationProject.dto.notification;
//
//import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
//import com.abdoatiia542.GraduationProject.model.Notification;
//import com.abdoatiia542.GraduationProject.model.User;
//import com.abdoatiia542.GraduationProject.model.UserNotification;
//import com.abdoatiia542.GraduationProject.saver.NotificationRequestSaver;
//import com.abdoatiia542.GraduationProject.service.notification.INotificationManagementService;
//import com.abdoatiia542.GraduationProject.specification.UserSpecification;
//import com.abdoatiia542.GraduationProject.utils.ContextHolderUtils;
////import com.abdoatiia542.GraduationProject.utils.NotificationUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Service
//@RequiredArgsConstructor
//public class NotificationManagementService implements INotificationManagementService {
//    private final NotificationUtils notificationUtils;
//    private final NotificationRequestSaver notificationRequestSaver;
//
//
////    Override
////    public Object createNotification(CreateNotificationByRoleRequest request) {
////        Notification savedNotification = notificationRequestSaver.apply(request);
////
////        Specification<User> specification = Specification
////                .where(UserSpecification.inSchool(contextHolderUtils.getSchool().getId()))
////                .and(UserSpecification.hasAnyRole(request.roles()));
////
////        CompletableFuture<List<User>> usersFuture = CompletableFuture
////                .supplyAsync(() -> userRepository.findAll(specification), executor);
////
////        AtomicReference<List<UserNotification>> userNotifications = new AtomicReference<>(new ArrayList<>());
////        usersFuture.thenAcceptAsync(users -> {
////            userNotifications.set(userNotificationSaver.apply(savedNotification, users));
////        }, executor);
////
////        notificationUtils.sendNotificationToDeviceTokens(usersFuture, savedNotification);
////
////        sendNotificationsCountToUsersOnRole(request.roles());
////
////        notificationUtils.sendRealTimeNotifications(usersFuture, userNotifications);
////
////        return ApiResponse.of("Notification created and sent successfully");
////    }
////
//    @Override
//    public Object getCountOfUnreadNotifications() {
//        User user = ContextHolderUtils.getUser();
//        return ApiResponse.of("Notifications count: ", notificationUtils
//                .getUnreadNotificationsCount(user.getId()));
//    }
//
//
//}
