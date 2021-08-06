package notificationsystem;

import notificationsystem.controller.CheckerUtil;
import notificationsystem.controller.Controller;
import notificationsystem.model.SubscriptionDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
