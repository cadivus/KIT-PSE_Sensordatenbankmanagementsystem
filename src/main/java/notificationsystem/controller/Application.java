package notificationsystem.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    private MailManager mailManager;
    private CheckerUtil checkerUtil;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/getConfirmCode")
    public String getConfirmCode(String mailAddress) {
        return (null);
    }

    @PostMapping("/postNewSubscriber")
    public void postNewSubscriber(String mailAddress) {

    }
}
