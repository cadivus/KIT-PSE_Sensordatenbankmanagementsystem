package notificationsystem.controller;

import notificationsystem.model.Sensor;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import notificationsystem.view.ConfirmationMail;
import notificationsystem.view.MailBuilder;
import notificationsystem.view.Report;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;
import java.util.List;
import java.util.UUID;

/**
 * The Controller is a central component to the E-Mail Notification System managing most tasks as well as providing an
 * API to outside systems. It is the only class to communicate with the classes from the 'model' and 'view' packages.
 * The CheckerUtil class can call the Controller to instruct it to send a report about a certain sensor to a subscriber
 * of that sensor, or to instruct it to send an alert about the malfunction of a certain sensor to one or multiple
 * subscribers.
 * The project website may call the Controller to issue a confirmation mail containing a confirmation code to be sent to
 * a given e-mail address.
 * Furthermore, the Controller allows the website to add, delete or get information about subscriptions.
 */
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

    @GetMapping("/getConfirmCode/{mailAddress}")
    public String getConfirmCode(@PathVariable MailAddress mailAddress) {
        ConfirmationMail confirmationMail = mailBuilder.buildConfirmationMail(mailAddress);
        mailSender.send(confirmationMail);
        return confirmationMail.getConfirmCode().getCode();
    }

    @PostMapping("/postSubscription")
    public void postSubscription(MailAddress mailAddress, UUID sensorID, Period reportInterval) {
        //subscriptionDAO.save(subscription);
    }

    @PostMapping("/postUnsubscribe")
    public void postUnsubscribe(MailAddress mailAddress, UUID sensorID) {
        Subscription toDelete = subscriptionDAO.get(mailAddress, sensorID);
        subscriptionDAO.delete(toDelete);
    }

    @GetMapping("/getSubscriptions/{mailAddress}")
    public List<UUID> getSubscriptions(@PathVariable String mailAddress) {
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
