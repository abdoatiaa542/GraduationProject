//package com.abdoatiia542.GraduationProject.saver;
//
//import com.abdoatiia542.GraduationProject.model.Notification;
//import com.abdoatiia542.GraduationProject.model.User;
//import com.abdoatiia542.GraduationProject.model.UserNotification;
//import com.abdoatiia542.GraduationProject.repository.UserNotificationRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.function.BiFunction;
//
//@Component
//@RequiredArgsConstructor
//public class UserNotificationSaver
//        implements BiFunction<Notification, Collection<User>, List<UserNotification>> {
//
//    private final UserNotificationRepository userNotificationRepository;
//
//    @Transactional
//    @Override
//    public List<UserNotification> apply(Notification notification, Collection<User> users) {
//        List<UserNotification> userNotifications = users
//                .stream()
//                .map(user -> UserNotification
//                        .builder()
//                        .notification(notification)
//                        .user(user)
//                        .build()
//                )
//                .toList();
//
//        return userNotificationRepository.saveAll(userNotifications);
//    }
//
//}
