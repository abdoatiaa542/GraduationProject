package com.abdoatiia542.GraduationProject.service.auth;

public interface IEmailSender {

    void sendResetPasswordEmail(EmailTemplate template) ;

    void sendRegisterConfirmationEmail(EmailTemplate template);
}
