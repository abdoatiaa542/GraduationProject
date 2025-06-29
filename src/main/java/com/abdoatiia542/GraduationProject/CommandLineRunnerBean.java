package com.abdoatiia542.GraduationProject;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.Gender;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineRunnerBean {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            log.info("Command line runner start");
            inserUser();

            log.info("CommandLineRunnerBean initialized successfully");
        };
    }

    void inserUser() {
        if (userRepository.count() == 0) {

            Trainee trainee1 = Trainee.builder()
                    .username("abdo")
                    .email("abdo@example.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
                    .enabled(true)
                    .height(175.0)
                    .weight(75.0)
                    .targetWeight(70.0)
                    .build();

            Trainee trainee2 = Trainee.builder()
                    .username("amer")
                    .email("amer@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.TRAINEE)
                    .gender(Gender.FEMALE)
                    .enabled(true)
                    .height(165.0)
                    .weight(60.0)
                    .targetWeight(55.0)
                    .build();

            Trainee trainee3 = Trainee.builder()
                    .username("mohamed03")
                    .email("mohamed03@example.com")
                    .password(passwordEncoder.encode("pass1234"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
                    .enabled(true)
                    .height(180.0)
                    .weight(90.0)
                    .targetWeight(80.0)
                    .build();

            userRepository.save(trainee1);
            userRepository.save(trainee2);
            userRepository.save(trainee3);

        }
    }


}