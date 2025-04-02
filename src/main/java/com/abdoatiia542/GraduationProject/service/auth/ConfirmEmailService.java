package com.abdoatiia542.GraduationProject.service.auth;

import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.model.RegistrationOTP;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.RegistrationOTPRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.db.DatabaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@RequiredArgsConstructor
public class ConfirmEmailService implements IConfirmEmailService {

    private final UserRepository userRepository;
    private final IEmailSender emailSender;
    private final RegistrationOTPRepository registrationOTPRepository;

    private final static ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Transactional
    @Override
    public void sendConfirmationEmail(Long userId, String email) {
        User user = DatabaseService.get(userRepository::findById, userId, User.class);

        String processedEmail = email.strip().toLowerCase();
        if (!user.getEmail().equals(processedEmail)) {
            throw new IllegalArgumentException("Email not found for user");
        }

        registrationOTPRepository.deleteAllByEmailIgnoreCase(processedEmail);

        CompletableFuture
                .supplyAsync(() -> {
                    int code;
                    do {
                        code = generateCode();
                    } while (registrationOTPRepository.existsByCode(code));
                    return code;
                }, executor)
                .thenApplyAsync((code) -> {
                    RegistrationOTP registrationOTP = registrationOTPRepository.save(
                            RegistrationOTP
                                    .builder()
                                    .user(user)
                                    .email(processedEmail)
                                    .code(code)
                                    .createdAt(LocalDateTime.now())
                                    .expiresAt(LocalDateTime.now().plusMinutes(3))
                                    .build()
                    );
                    return getTemplate(registrationOTP);
                }, executor)
                .thenAcceptAsync(emailSender::sendRegisterConfirmationEmail, executor);

    }

    @Transactional
    @Override
    public ApiResponse sendConfirmationEmailWithResponse(Long userId, String email) {
        User user = DatabaseService.get(userRepository::findById, userId, User.class);

        String processedEmail = email.strip().toLowerCase();
        if (!user.getEmail().equals(processedEmail)) {
            throw new IllegalArgumentException("Email not found for user");
        }

        registrationOTPRepository.deleteAllByEmailIgnoreCase(processedEmail);

        CompletableFuture
                .supplyAsync(() -> {
                    int code;
                    do {
                        code = generateCode();
                    } while (registrationOTPRepository.existsByCode(code));
                    return code;
                }, executor)
                .thenApplyAsync((code) -> {
                    RegistrationOTP registrationOTP = registrationOTPRepository.save(
                            RegistrationOTP
                                    .builder()
                                    .user(user)
                                    .email(processedEmail)
                                    .code(code)
                                    .createdAt(LocalDateTime.now())
                                    .expiresAt(LocalDateTime.now().plusMinutes(3))
                                    .build()
                    );
                    return getTemplate(registrationOTP);
                }, executor)
                .thenAcceptAsync(emailSender::sendRegisterConfirmationEmail, executor);

        return ApiResponse.of("Confirmation OTP generated and sent successfully");
    }

    @Override
    public ApiResponse confirmAccount(String email, int code) {
        RegistrationOTP otp = getRegistrationOTP(email, code);

        User user = otp.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        registrationOTPRepository.delete(otp);

        return ApiResponse.of("Email confirmed successfully");
    }

    private static int generateCode() {
        SecureRandom random = new SecureRandom();
        int code = 0;
        for (int counter = 0; counter < 6; counter++) {
            code = (code * 10) + 1 + random.nextInt(9);
        }
        return code;
    }

    private static EmailTemplate getTemplate(RegistrationOTP otp) {
        return new EmailTemplate() {
            @Override
            public String receiverEmail() {
                return otp.getEmail();
            }

            @Override
            public int code() {
                return otp.getCode();
            }

            @Override
            public LocalDateTime requestDate() {
                return otp.getCreatedAt();
            }

            @Override
            public LocalDateTime expirationDate() {
                return otp.getExpiresAt();
            }
        };
    }

    private RegistrationOTP getRegistrationOTP(String email, int code) {
        String processedEmail = email.strip().toLowerCase();
        Optional<RegistrationOTP> otpOptional = registrationOTPRepository
                .findByEmailIgnoreCase(processedEmail);

        if (otpOptional.isEmpty()) {
            throw new IllegalArgumentException("Confirmation code not found for email");
        }

        RegistrationOTP otp = otpOptional.get();
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Confirmation code expired, %s".formatted(otp.getExpiresAt()));
        }

        if (otp.getCode() != code) {
            throw new IllegalArgumentException("Confirmation code mismatch");
        }

        return otp;
    }

}
