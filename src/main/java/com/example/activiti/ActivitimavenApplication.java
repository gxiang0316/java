package com.example.activiti;

import org.activiti.spring.boot.JpaProcessEngineAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class ActivitimavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitimavenApplication.class, args);
    }
}
