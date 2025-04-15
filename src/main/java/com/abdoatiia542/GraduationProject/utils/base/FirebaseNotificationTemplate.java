package com.abdoatiia542.GraduationProject.utils.base;

import com.abdoatiia542.GraduationProject.model.enumerations.NotificationType;
import org.springframework.lang.Nullable;

public interface FirebaseNotificationTemplate {

    String getTitle();

    String getBody();

    NotificationType getType();

    @Nullable
    default String getImage() {
        return null;
    }

}
