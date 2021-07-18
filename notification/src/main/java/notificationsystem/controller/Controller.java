package notificationsystem.controller;

import notificationsystem.model.Sensor;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import notificationsystem.view.Alert;
import notificationsystem.view.ConfirmationMail;
import notificationsystem.view.MailBuilder;
import notificationsystem.view.Report;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;
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
 * The Controller class contains an instance of a MailBuilder, a MailSender, a SubscriptionDAO and a SensorDAO.
 */
@RestController
public class Controller {

    private MailBuilder mailBuilder;
    private MailSender mailSender;
    private SubscriptionDAO subscriptionDAO;
    private SensorDAO sensorDAO;

    /**
     * Constructs a new Controller instance. Instantiates the MailBuilder, MailSender, SubscriptionDAO and SensorDAO.
     */
    public Controller() {
        this.mailBuilder = new MailBuilder();
        this.mailSender = new MailSender();
        this.subscriptionDAO = new SubscriptionDAO();
        this.sensorDAO = new SensorDAO();

    }

    /**
     * Sends a confirmation mail to a user.
     * The method first uses the MailBuilder class to build the e-mail, then the MailSender class to send it to its
     * recipient.
     * @param mailAddress e-mail address the confirmation mail is sent to.
     * @return String containing the confirmation code sent to the user.
     */
    @GetMapping("/getConfirmCode/{mailAddress}")
    public String getConfirmCode(@PathVariable String mailAddress) {
        ConfirmationMail confirmationMail = mailBuilder.buildConfirmationMail(mailAddress);
        try {
            mailSender.send(confirmationMail);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return confirmationMail.getConfirmCode();
    }

    /**
     * The postSubscription method allows the project website to add a new subscription to the database.
     * A subscription is always between one subscriber and one sensor. To allow regular reports to be sent, it
     * furthermore contains the time period between these reports. Internally a timestamp is saved marking the beginning
     * of the first time interval.
     * @param mailAddress e-mail address of the subscriber.
     * @param sensorID ID of the sensor the user subscribes to.
     * @param reportInterval time period between reports.
     */
    @PostMapping("/postSubscription")
    public void postSubscription(String mailAddress, UUID sensorID, long reportInterval) {
        Subscription subscription = new Subscription(mailAddress, sensorID, LocalDate.now(), reportInterval);
        subscriptionDAO.save(subscription);
    }

    /**
     * The postUnsubscribe method allows the project website to delete a subscription from the database.
     * @param mailAddress e-mail of the user unsubscribing.
     * @param sensorID ID of the sensor the user unsubscribes from.
     */
    @PostMapping("/postUnsubscribe")
    public void postUnsubscribe(String mailAddress, UUID sensorID) {
        Subscription toDelete = subscriptionDAO.get(mailAddress, sensorID);
        subscriptionDAO.delete(toDelete);
    }

    /**
     * The getSubscription method allows the project website to inquire about the sensors a user is subscribed to.
     * @param mailAddress e-mail of the subscriber.
     * @return List of the sensors the user is subscribed to. The list contains the UUIDs of those sensors.
     */
    @GetMapping("/getSubscriptions/{mailAddress}")
    public List<UUID> getSubscriptions(@PathVariable String mailAddress) {
        return subscriptionDAO.getAllSensors(mailAddress);
    }

    /**
     * The getAlert method is used to send an alert e-mail to all subscribers of a given sensor when that sensor
     * malfunctions or fails. The method uses the SensorDAO class to retrieve the subscribers of the given sensor, then
     * builds and sends the mail using the MailBuilder and MailSender classes.
     * The method is only called by the CheckerUtil class.
     * @param sensorID ID of the sensor malfunctioning.
     */
    public void sendAlert(UUID sensorID) {
        Sensor sensor = sensorDAO.get(sensorID);
        List<String> subscribers = subscriptionDAO.getAllSubscribers(sensorID);
        for (String subscriber : subscribers) {
            Alert alert = mailBuilder.buildAlert(subscriber, sensor);
            try {
                mailSender.send(alert);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The getReport methos is used to sent report e-mails to a subscriber of the given sensor. Reports contain
     * relevant information about the sensor and the data collected.
     * The method uses the SensorDAO class to retrieve the information about the sensor and its data. The e-mail is
     * then built and sent using the MailBuilder and MailSender classes, respectively.
     * The method is only called by the CheckerUtil class.
     * @param mailAddress e-mail address of the subscriber the report is sent to.
     * @param sensorID ID of the sensor the report is about.
     */
    public void sendReport(String mailAddress, UUID sensorID) {
        Sensor sensor = sensorDAO.get(sensorID);
        Report report = mailBuilder.buildReport(mailAddress, sensor);
        try {
            mailSender.send(report);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}