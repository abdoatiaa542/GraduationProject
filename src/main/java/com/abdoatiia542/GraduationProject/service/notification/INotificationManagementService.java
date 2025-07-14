package com.abdoatiia542.GraduationProject.service.notification;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.notification.CreateNotificationByRoleRequest;

public interface INotificationManagementService {


    Object getCountOfUnreadNotifications();

    ApiResponse createNotification(CreateNotificationByRoleRequest request);
}
