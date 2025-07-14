package com.abdoatiia542.GraduationProject.configuration.firebase;

import com.abdoatiia542.GraduationProject.service.jwt.BearerTokenWrapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@EnableScheduling
@Configuration
public class ApplicationConfiguration {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BearerTokenWrapper bearerTokenWrapper() {
        return new BearerTokenWrapper();
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/fit-ai-firebase-admin-sdk.json");

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(resource.getInputStream());

        FirebaseOptions options = FirebaseOptions
                .builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp app;
        if (FirebaseApp.getApps().isEmpty()) {
            app = FirebaseApp.initializeApp(options);
        } else {
            app = FirebaseApp.getInstance();
        }

        return FirebaseMessaging.getInstance(app);
    }

}
