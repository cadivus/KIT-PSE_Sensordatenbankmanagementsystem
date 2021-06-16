package notificationsystem.controller;

import notificationsystem.configuration.AppConfig;
import notificationsystem.model.SensorName;
import notificationsystem.view.ConfirmCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;
import java.util.UUID;

@SpringBootApplication
@RestController
public class Application {

    private MailManager mailManager;
    private SubscriptionManager subManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        ApplicationContext factory = new AnnotationConfigApplicationContext(AppConfig.class);

        CheckerUtil checkerUtil = CheckerUtil.getInstance();

    }

    @GetMapping("/getConfirmCode")
    public String getConfirmCode(MailAddress mailAddress) {
        ConfirmCode code = mailManager.confirmMail(mailAddress);
        return(code.getCode());
    }

    @PostMapping("/postSubscription")
    public void postSubscription(MailAddress mailAddress, UUID sensorID, Period reportInterval) {
        //subManager.addSubscription();
    }

    @PostMapping("/postUnsubscribe")
    public void postUnsubscribe(MailAddress mailAddress, UUID sensorID) {
        //subManager.deleteSubscription();
    }
}
