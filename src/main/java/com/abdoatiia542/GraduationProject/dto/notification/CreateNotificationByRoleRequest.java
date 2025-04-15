package com.abdoatiia542.GraduationProject.dto.notification;

import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

public record CreateNotificationByRoleRequest(
        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        @NotEmpty
        Set<Role> roles
) implements NotificationRequestTemplate, Serializable {
}