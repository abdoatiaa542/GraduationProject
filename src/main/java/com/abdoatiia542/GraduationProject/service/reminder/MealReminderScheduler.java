package com.abdoatiia542.GraduationProject.service.reminder;

import com.abdoatiia542.GraduationProject.model.Notification;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.model.enumerations.NotificationType;
import com.abdoatiia542.GraduationProject.repository.NotificationRepository;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.utils.notification.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealReminderScheduler {

    private final TraineeRepository traineeRepository;
    private final NotificationUtils notificationUtils;
    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "0 0 9 * * *")
    public void sendBreakfastReminders() {
        sendMealReminder("Good Morning!", "Don't skip your breakfast – fuel your day with energy!");
    }

    @Scheduled(cron = "0 0 14 * * *") //
    public void sendLunchReminders() {
        sendMealReminder("It's Lunch Time!", "Take a break and enjoy your healthy lunch!");
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void sendDinnerReminders() {
        sendMealReminder("Dinner Reminder", "Time for a light dinner to end the day right!");
    }


    // send notification for all users in a specific time
    private void sendMealReminder(String title, String message) {
        List<Trainee> trainees = traineeRepository.findAll();
        List<User> users = trainees.stream()  // casting
                .map(trainee -> (User) trainee)
                .toList();
        Notification notification = Notification
                .builder()
                .title(title)
                .description(message)
                .type(NotificationType.NOTIFICATION)
                .build();
        notificationRepository.save(notification);
        notificationUtils.sendNotificationToDeviceTokens(users, notification);
    }
}