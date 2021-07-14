package edu.teco.sensordatenbankmanagementsystem;

import edu.teco.sensordatenbankmanagementsystem.configs.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

/**
 * The main class and entry point for this spring boot application.
 */
@SpringBootApplication(scanBasePackages = {"edu.teco.sensordatenbankmanagementsystem"}, exclude = {})
@EnableJpaRepositories(basePackages="edu.teco.sensordatenbankmanagementsystem.repository")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
