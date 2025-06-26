//package com.abdoatiia542.GraduationProject.controller;
//
//import com.abdoatiia542.GraduationProject.dto.notification.CreateNotificationByRoleRequest;
//import com.abdoatiia542.GraduationProject.dto.notification.NotificationManagementService;
//import com.abdoatiia542.GraduationProject.service.notification.INotificationManagementService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//
//@RestController
//@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'NOTIFICATION_MANAGER')")
//@RequestMapping(value = "api/v1/management/notification")
//public class NotificationManagementController {
//
//    private final INotificationManagementService service;
//
//    public NotificationManagementController(NotificationManagementService service) {
//        this.service = service;
//    }
//
//
////    @PostMapping(value = "users-by-role")
////    public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationByRoleRequest request) {
////        return ResponseEntity.created(URI.create("api/v1/management/notification/users-by-role"))
////                .body(service.createNotification(request));
////    }
//
//
//    @GetMapping(value = "count")
//    @PreAuthorize(value = "permitAll()")
//    public ResponseEntity<?> getCountOfUnreadNotifications() {
//        return ResponseEntity.ok()
//                .body(service.getCountOfUnreadNotifications());
//    }
//
//}