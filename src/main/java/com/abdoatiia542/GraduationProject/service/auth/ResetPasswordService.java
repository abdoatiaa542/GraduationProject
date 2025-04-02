package com.abdoatiia542.GraduationProject.service.auth;


import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.model.ResetPasswordToken;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.ResetPasswordTokenRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.db.DatabaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ResetPasswordService implements IResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IEmailSender resetPasswordEmailSender;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    private final static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public ApiResponse findAccount(String usernameOrEmail) {
        Optional<User> userOptional = userRepository.
                findByUsernameOrEmail(usernameOrEmail.strip());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Username or email not found");
        }

        User user = userOptional.get();
        if (!user.isEnabled()) {
            throw new IllegalArgumentException("User account is disabled");
        }

        if (!user.isAccountNonLocked()) {
            throw new IllegalArgumentException("User account is locked");
        }

        List<String> emails = getUserEmails(user);

        Map<String, Object> response = new HashMap<>();
        response.put("emails", emails);
        response.put("userId", user.getId());

        return ApiResponse.of("User account found successfully, this is available emails to reset password", response);
    }

    @Transactional
    @Override
    public ApiResponse requestResetPassword(Long userId, String email) {
        User user = DatabaseService.get(userRepository::findById, userId, User.class);
        List<String> emails = getUserEmails(user);

        String processedEmail = email.strip().toLowerCase();
        if (!emails.stream().map(emailFromStream -> emailFromStream.toLowerCase()).toList().contains(processedEmail)) {
            throw new IllegalArgumentException("Email not found for user");
        }

        resetPasswordTokenRepository.deleteAllByEmailIgnoreCase(processedEmail);

        CompletableFuture
                .supplyAsync(() -> {
                    int code;
                    do {
                        code = generateCode();
                    } while (resetPasswordTokenRepository.existsByCode(code));
                    return code;
                }, executor)
                .thenApplyAsync((code) -> {
                    ResetPasswordToken savedResetPasswordToken = resetPasswordTokenRepository.save(
                            ResetPasswordToken
                                    .builder()
                                    .user(user)
                                    .email(processedEmail)
                                    .code(code)
                                    .createdAt(LocalDateTime.now())
                                    .expiresAt(LocalDateTime.now().plusMinutes(3))
                                    .build()
                    );
                    return getTemplate(savedResetPasswordToken);
                }, executor)
                .thenAcceptAsync(resetPasswordEmailSender::sendResetPasswordEmail, executor);

        return ApiResponse.of("Reset password email generated and sent successfully");
    }

    @Override
    public ApiResponse checkResetPasswordCode(String email, int code) {
        getResetPasswordToken(email, code);
        return ApiResponse.of("Reset password code is validated successfully");
    }

    @Override
    public ApiResponse resetPassword(String email, int code, String password) {
        ResetPasswordToken token = getResetPasswordToken(email, code);

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        resetPasswordTokenRepository.delete(token);

        return ApiResponse.of("Password reset successfully");
    }

    private static int generateCode() {
        SecureRandom random = new SecureRandom();
        int code = 0;
        for (int counter = 0; counter < 6; counter++) {
            code = (code * 10) + 1 + random.nextInt(9);
        }
        return code;
    }

    private static List<String> getUserEmails(User user) {
        List<String> emails = new ArrayList<>();
        emails.add(user.getEmail());
        if (user instanceof Trainee trainee) {
            emails.add(trainee.getEmail());
        }
        return emails;
    }

    private static EmailTemplate getTemplate(ResetPasswordToken token) {
        return new EmailTemplate() {
            @Override
            public String receiverEmail() {
                return token.getEmail();
            }

            @Override
            public int code() {
                return token.getCode();
            }

            @Override
            public LocalDateTime requestDate() {
                return token.getCreatedAt();
            }

            @Override
            public LocalDateTime expirationDate() {
                return token.getExpiresAt();
            }
        };
    }

    private ResetPasswordToken getResetPasswordToken(String email, int code) {
        String processedEmail = email.strip().toLowerCase();
        Optional<ResetPasswordToken> tokenOptional = resetPasswordTokenRepository
                .findByEmailIgnoreCase(processedEmail);

        if (tokenOptional.isEmpty()) {
            throw new IllegalArgumentException("Reset code not found for email");
        }

        ResetPasswordToken token = tokenOptional.get();
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reset code expired, %s".formatted(token.getExpiresAt()));
        }

        if (token.getCode() != code) {
            throw new IllegalArgumentException("Reset code mismatch");
        }

        return token;
    }

}
