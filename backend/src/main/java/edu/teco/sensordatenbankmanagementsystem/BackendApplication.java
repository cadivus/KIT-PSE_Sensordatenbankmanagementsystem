package edu.teco.sensordatenbankmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main class and entry point for this spring boot application.
 */
@SpringBootApplication(scanBasePackages = {"edu.teco.sensordatenbankmanagementsystem"})
@EnableJpaRepositories(basePackages="edu.teco.sensordatenbankmanagementsystem.repository")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
