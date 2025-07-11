package com.abdoatiia542.GraduationProject.service.notification;


public interface IUserNotificationService {
    Object getNotification(Long id);

    Object readNotification(Long id);

    Object deleteNotification(Long id);

    Object getAllNotifications(int pageSize, int pageNumber);

    Object readAllNotifications();
}