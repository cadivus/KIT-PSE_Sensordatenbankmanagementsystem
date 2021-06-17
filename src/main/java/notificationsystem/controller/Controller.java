package notificationsystem.controller;

import notificationsystem.model.Sensor;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import notificationsystem.view.ConfirmationMail;
import notificationsystem.view.MailBuilder;
import notificationsystem.view.Report;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {

    private MailBuilder mailBuilder;
    private MailSender mailSender;
    private SubscriptionDAO subscriptionDAO;
    private SensorDAO sensorDAO;

    public Controller() {
        this.mailBuilder = new MailBuilder();
        this.mailSender = new MailSender();
        this.subscriptionDAO = new SubscriptionDAO();
        this.sensorDAO = new SensorDAO();

    }

    //ggf requestmapping
    @GetMapping("/getConfirmCode")
    public String getConfirmCode(MailAddress mailAddress) {
        ConfirmationMail confirmationMail = mailBuilder.buildConfirmationMail(mailAddress);
        mailSender.send(confirmationMail);
        return confirmationMail.getConfirmCode().getCode();
    }

    //Request body, Response body, Path-variable, @enablewebsecurity
    @PostMapping("/postSubscription")
    public void postSubscription(MailAddress mailAddress, UUID sensorID, Period reportInterval) {
        //subscriptionDAO.save(subscription);
    }

    @PostMapping("/postUnsubscribe")
    public void postUnsubscribe(MailAddress mailAddress, UUID sensorID) {
        Subscription toDelete = subscriptionDAO.get(mailAddress, sensorID);
        subscriptionDAO.delete(toDelete);
    }

    @GetMapping("/getSubscription")
    public List<UUID> getSubscriptions(String mailAddress) {
        return subscriptionDAO.getAllSensors(mailAddress);
    }

    public void getAlert(UUID sensorID) {
        Sensor sensor = sensorDAO.get(sensorID);
        List<MailAddress> subscribers = subscriptionDAO.getAllSubscribers(sensorID);
        //für jeden Listeneintrag mailBuilder.buildAlert() aufrufen und dann abschicken
    }

    public void getReport(MailAddress mailAddress, UUID sensorID) {
        Sensor sensor = sensorDAO.get(sensorID);
        Report report = mailBuilder.buildReport(mailAddress, sensor);
        mailSender.send(report);
    }

}
