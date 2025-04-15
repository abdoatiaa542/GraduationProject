//package com.abdoatiia542.GraduationProject.utils;
//
//import com.abdoatiia542.GraduationProject.mapper.notification.UserNotificationResponseMapper;
//import com.abdoatiia542.GraduationProject.model.Notification;
//import com.abdoatiia542.GraduationProject.model.User;
//import com.abdoatiia542.GraduationProject.model.UserNotification;
//import com.abdoatiia542.GraduationProject.repository.NotificationRepository;
//import com.abdoatiia542.GraduationProject.repository.UserNotificationRepository;
//import com.abdoatiia542.GraduationProject.saver.FirebaseNotificationSender;
//import com.abdoatiia542.GraduationProject.saver.UserNotificationSaver;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Component
//@RequiredArgsConstructor
//public class NotificationUtils {
//    private final UserNotificationRepository userNotificationRepository;
//    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//    private final FirebaseNotificationSender firebaseNotificationSender;
//    private final UserNotificationResponseMapper userNotificationResponseMapper;
//    private final SimpMessagingTemplate simpMessagingTemplate;
//    private final NotificationRepository notificationRepository;
//    private final UserNotificationSaver userNotificationSaver;
//
//    public Integer getUnreadNotificationsCount(Long userId) {
//
//        return userNotificationRepository.countByUser_IdAndReadIsFalse(userId);
//    }
//
//    public static List<String> getDeviceTokens(Collection<? extends User> users) {
//        return users.stream()
//                .map(User::getDeviceTokens)
//                .flatMap(Collection::stream)
//                .toList();
//    }
//
//    public void sendNotificationToDeviceTokens(CompletableFuture<List<User>> usersFuture, Notification savedNotification) {
//        CompletableFuture<Collection<String>> deviceTokensFuture = usersFuture
//                .thenApplyAsync(NotificationUtils::getDeviceTokens, executor);
//
//        deviceTokensFuture.thenAcceptAsync(deviceTokens -> firebaseNotificationSender.accept(savedNotification, deviceTokens), executor);
//    }
//
//    public void sendRealTimeNotifications(CompletableFuture<List<User>> usersFuture, AtomicReference<List<UserNotification>> userNotifications) {
//        usersFuture.thenAcceptAsync(users -> {
//            userNotifications.get().forEach(userNotification ->
//                    simpMessagingTemplate.convertAndSendToUser(userNotification.getUser().getId().toString(), "/notifications", userNotificationResponseMapper.apply(userNotification))
//            );
//
//        }, executor);
//    }
//
//    public void sendNotificationToDeviceTokens(List<User> usersList, Notification notification) {
//        List<String> deviceTokens = NotificationUtils.getDeviceTokens(usersList);
//        firebaseNotificationSender.accept(notification, deviceTokens);
//    }
//
//    public void sendNotificationToDeviceTokensAndRealTimeChannel(Notification notification, List<User> usersList) {
//        Notification savedNotification = notificationRepository.save(notification);
//
//        List<UserNotification> userNotifications = userNotificationSaver.apply(savedNotification, usersList);
//
//        userNotifications.forEach(userNotification ->
//                simpMessagingTemplate.convertAndSendToUser(userNotification.getUser().getId().toString(), "/notifications", userNotificationResponseMapper.apply(userNotification))
//        );
//
//        sendNotificationToDeviceTokens(usersList, savedNotification);
//    }
//}
