package notificationsystem;

import notificationsystem.controller.CheckerUtil;
import notificationsystem.controller.Controller;
import notificationsystem.view.MailBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Eigene Klasse, @enableautoconfiguration, in keinem package
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        CheckerUtil checkerUtil = CheckerUtil.getInstance();
        Controller controller = new Controller();

    }
}
