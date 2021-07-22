package notificationsystem;

import notificationsystem.controller.CheckerUtil;
import notificationsystem.controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        CheckerUtil checkerUtil = CheckerUtil.getInstance();
        Controller controller = new Controller();

    }
}
