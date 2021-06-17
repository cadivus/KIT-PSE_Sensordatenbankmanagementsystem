package edu.teco.sensordatenbankmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class and entry point for this spring boot application.
 */
@SpringBootApplication(scanBasePackages={"edu.teco.sensordatenbankmanagement"})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
