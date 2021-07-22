package notificationsystem;

import notificationsystem.controller.CheckerUtil;
import notificationsystem.controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        CheckerUtil checkerUtil = CheckerUtil.getInstance();
        Controller controller = new Controller();

    }
}
