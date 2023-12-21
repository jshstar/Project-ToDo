package com.sparta.project_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ProjectTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectTodoApplication.class, args);
    }

}
