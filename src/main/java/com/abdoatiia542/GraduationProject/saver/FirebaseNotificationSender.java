package com.abdoatiia542.GraduationProject.saver;

import com.abdoatiia542.GraduationProject.utils.base.FirebaseNotificationTemplate;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseNotificationSender
        implements BiConsumer<FirebaseNotificationTemplate, Collection<String>> {

    private final FirebaseMessaging firebaseMessaging;
    private final Logger logger = LoggerFactory.getLogger(FirebaseNotificationSender.class);

    @Override
    public void accept(FirebaseNotificationTemplate template, Collection<String> deviceTokens) {
        Notification notification = Notification
                .builder()
                .setTitle(template.getTitle())
                .setBody(template.getBody())
                .setImage(template.getImage())
                .build();

        final Function<String, Message> mapper = deviceToken -> Message
                .builder()
                .setNotification(notification)
                .setToken(deviceToken)
                .putData("type", template.getType().toString())
                .build();

        List<Message> messages = deviceTokens
                .stream()
                .map(mapper)
                .toList();

        try {
            if (!messages.isEmpty()) {
                BatchResponse response = firebaseMessaging.sendEach(messages);

                Consumer<String> logger = response.getFailureCount() == 0
                        ? log::info
                        : log::warn;

                logger.accept("Firebase notification sent successfully, success %s, failure %s"
                        .formatted(response.getSuccessCount(), response.getFailureCount()));
            } else {
                logger.info("No notifications to send through firebase");
            }
        } catch (FirebaseMessagingException exception) {
            log.warn("Exception while sending firebase notification", exception);
        }
    }

}