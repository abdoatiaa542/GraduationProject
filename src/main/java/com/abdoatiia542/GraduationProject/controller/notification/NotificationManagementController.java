
package com.abdoatiia542.GraduationProject.controller.notification;

import com.abdoatiia542.GraduationProject.service.notification.INotificationManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/management/notification")
public class NotificationManagementController {

    private final INotificationManagementService service;

    public NotificationManagementController(INotificationManagementService service) {
        this.service = service;
    }


//    @PostMapping(value = "users-by-role")
//    public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationByRoleRequest request) {
//        return ResponseEntity.created(URI.create("api/v1/management/notification/users-by-role"))
//                .body(service.createNotification(request));
//}


    @GetMapping(value = "count")
    public ResponseEntity<?> getCountOfUnreadNotifications() {
        return ResponseEntity.ok()
                .body(service.getCountOfUnreadNotifications());
    }

}