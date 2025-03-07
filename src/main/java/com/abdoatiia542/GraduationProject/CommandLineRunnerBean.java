package com.abdoatiia542.GraduationProject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineRunnerBean {

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("Command line runner start");
            /*
            *
            *
            *
            *
            *
            *
            *
            *
            * */
            log.info("CommandLineRunnerBean initialized successfully");
        };
    }

}
