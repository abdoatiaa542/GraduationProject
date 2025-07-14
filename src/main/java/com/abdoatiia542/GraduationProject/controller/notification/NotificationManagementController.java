
package com.abdoatiia542.GraduationProject.controller.notification;

import com.abdoatiia542.GraduationProject.dto.notification.CreateNotificationByRoleRequest;
import com.abdoatiia542.GraduationProject.service.notification.INotificationManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/management/notification")
public class NotificationManagementController {

    private final INotificationManagementService service;

    public NotificationManagementController(INotificationManagementService service) {
        this.service = service;
    }


    @PostMapping(value = "users-by-role")
    public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationByRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createNotification(request));
    }


    @GetMapping(value = "count")
    public ResponseEntity<?> getCountOfUnreadNotifications() {
        return ResponseEntity.ok()
                .body(service.getCountOfUnreadNotifications());
    }

}