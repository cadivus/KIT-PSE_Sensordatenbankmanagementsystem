package notificationsystem;

import notificationsystem.controller.CheckerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Eigene Klasse, @enableautoconfiguration, in keinem package
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        CheckerUtil checkerUtil = CheckerUtil.getInstance();

    }
}
