//package com.abdoatiia542.GraduationProject.service.auth;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import java.time.format.DateTimeFormatter;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class SimpleEmailSender implements IEmailSender {
//
//    private final JavaMailSender javaMailSender;
//    private final TemplateEngine templateEngine;
//
//    @Value(value = "${spring.mail.username}")
//    private String from;
//
//    @Override
//    public void sendResetPasswordEmail(EmailTemplate template) {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(from);
//            helper.setSubject("Reset EDU AI Account Password");
//            helper.setTo(template.receiverEmail());
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
//
//            Context context = new Context();
//            context.setVariable("code", template.code());
//            context.setVariable("requested", template.requestDate().format(formatter));
//            context.setVariable("expiration", template.expirationDate().format(formatter));
//
//            String content = templateEngine.process("reset-password-view.html", context);
//            helper.setText(content, true);
//
//            javaMailSender.send(message);
//        } catch (MessagingException exception) {
//            log.error("Failed to send reset password email", exception);
//        }
//    }
//
//    @Override
//    public void sendRegisterConfirmationEmail(EmailTemplate template) {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(from);
//            helper.setSubject("Confirm EDU AI Account Email");
//            helper.setTo(template.receiverEmail());
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
//
//            Context context = new Context();
//            context.setVariable("code", template.code());
//            context.setVariable("requested", template.requestDate().format(formatter));
//            context.setVariable("expiration", template.expirationDate().format(formatter));
//
//            String content = templateEngine.process("confirm-email-view.html", context);
//            helper.setText(content, true);
//
//            javaMailSender.send(message);
//        } catch (MessagingException exception) {
//            log.error("Failed to send confirmation email", exception);
//        }
//    }
//
//}
