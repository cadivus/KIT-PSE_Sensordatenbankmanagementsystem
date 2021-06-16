package notificationsystem.controller;

import notificationsystem.configuration.AppConfig;
import notificationsystem.view.ConfirmCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getConfirmCode(String address) {
        MailAddress mailAddress = new MailAddress(address);
        ConfirmCode code = mailManager.confirmMail(mailAddress);
        return (code.getCode());
    }

    @PostMapping("/postSubscription")
    public void postSubscription(String mailAddress, String sensorName) {
        //subManager.addSubscription();
    }

    @PostMapping("/postUnsubscribe")
    public void postUnsubscribe(String mailAddress, String sensorName) {
        //subManager.deleteSubscription();
    }
}
