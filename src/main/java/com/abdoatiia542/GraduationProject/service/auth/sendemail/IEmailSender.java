package com.abdoatiia542.GraduationProject.service.auth.sendemail;

public interface IEmailSender {

    void sendResetPasswordEmail(EmailTemplate template) ;

    void sendRegisterConfirmationEmail(EmailTemplate template);
}
