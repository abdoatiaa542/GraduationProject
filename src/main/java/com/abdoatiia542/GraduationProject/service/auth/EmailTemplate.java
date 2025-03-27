package com.abdoatiia542.GraduationProject.service.auth;

import java.time.LocalDateTime;

public interface EmailTemplate {

    String receiverEmail();

    int code();

    LocalDateTime requestDate();

    LocalDateTime expirationDate();

}
