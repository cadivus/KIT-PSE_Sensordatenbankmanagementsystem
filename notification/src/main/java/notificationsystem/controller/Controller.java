package notificationsystem.controller;

import notificationsystem.model.*;
import notificationsystem.view.Alert;
import notificationsystem.view.ConfirmationMail;
import notificationsystem.view.MailBuilder;
import notificationsystem.view.Report;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @Value("${sensors.backend.url}")
    private String backendUrl;
    private final static String CONSTRUCTOR_ERROR = "No Email login data found.";

    private final MailBuilder mailBuilder;
    private final MailSender mailSender;
    private final SubscriptionDAO subscriptionDAO;
    private SensorDAO sensorDAO;
    private final static long SYSTEMLOGIN_ID = 1;
    private final RestTemplate restTemplate;
    private String port = "587";
    private String host = "smtp.gmail.com";

    @Autowired
    public Controller(SystemLoginDAO systemLoginDAO, SubscriptionDAO subscriptionDAO, RestTemplate restTemplate) throws Exception {
        this.mailBuilder = new MailBuilder();

        Optional<SystemLogin> loginOptional = systemLoginDAO.getLogin(SYSTEMLOGIN_ID);
        SystemLogin login;
        if (loginOptional.isPresent()) {
            login = loginOptional.get();
        } else {
            throw new Exception(CONSTRUCTOR_ERROR);
        }

        this.mailSender = new MailSender(login.getUsername(), login.getPassword(), port, host);
        this.subscriptionDAO = subscriptionDAO;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void postConstruct() {
        this.sensorDAO = new SensorDAO(backendUrl, restTemplate);
    }

    /**
     * Only for testing purposes.
     * @param port smtp server port.
     * @param host smtp server host.
     */
    public void setMailData(String port, String host) {
        this.port = port;
        this. host = host;
    }

    /**
     * Sends a confirmation mail to a user.
     * The method first uses the MailBuilder class to build the e-mail and generate the confirmation code,
     * then the MailSender class to send it to its recipient.
     * @param mailAddress e-mail address the confirmation mail is sent to.
     * @return String containing the confirmation code sent to the user.
     */
    @GetMapping("/getConfirmCode/{mailAddress}")
    public String getConfirmCode(@PathVariable String mailAddress) {
        ConfirmationMail confirmationMail = mailBuilder.buildConfirmationMail(mailAddress);
        try {
            mailSender.send(confirmationMail);
        } catch (MessagingException | UnsupportedEncodingException e) {
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
    @PostMapping(value = "/postSubscription", consumes = "application/json")
    public void postSubscription(@RequestParam("mailAddress") String mailAddress, @RequestParam("sensorID") String sensorID,
                                 @RequestParam("reportInterval") long reportInterval, @RequestParam("toggleAlert") boolean toggleAlert) {
        Subscription subscription = new Subscription(mailAddress, sensorID, LocalDate.now(), reportInterval, toggleAlert);
        subscriptionDAO.save(subscription);
    }

    /**
     * The postUnsubscribe method allows the project website to delete a subscription from the database.
     * @param mailAddress e-mail of the user unsubscribing.
     * @param sensorID ID of the sensor the user unsubscribes from.
     */
    @PostMapping(value = "/postUnsubscribe", consumes = "application/json")
    public void postUnsubscribe(@RequestParam("mailAddress") String mailAddress, @RequestParam("sensorID") String sensorID) {
        Subscription toDelete = subscriptionDAO.get(mailAddress, sensorID);
        subscriptionDAO.delete(toDelete);
    }

    /**
     * The getSubscription method allows the project website to inquire about the subscriptions in the database.
     * @param mailAddress e-mail of the subscriber.
     * @return List of the subscriptions of the user.
     */
    @GetMapping("/getSubscriptions/{mailAddress}")
    public List<Subscription> getSubscriptions(@PathVariable String mailAddress) {
        List<String> sensorIds = subscriptionDAO.getAllSensors(mailAddress);
        LinkedList<Subscription> subs = new LinkedList<>();
        for (String id : sensorIds) {
            subs.add(subscriptionDAO.get(mailAddress, id));
        }
        return subs;
    }

    /**
     * The getAlert method is used to send an alert e-mail to all subscribers of a given sensor when that sensor
     * malfunctions or fails. The method uses the SensorDAO class to retrieve the subscribers of the given sensor, then
     * builds and sends the mail using the MailBuilder and MailSender classes.
     * The method is only called by the CheckerUtil class.
     * @param sensorID ID of the sensor malfunctioning.
     */
    public void sendAlert(String sensorID) {
        Sensor sensor = sensorDAO.get(sensorID);
        List<String> subscribers = subscriptionDAO.getAllSubscribers(sensorID);
        for (String subscriber : subscribers) {
            if (subscriptionDAO.get(subscriber, sensorID).isToggleAlert()) {
            Alert alert = mailBuilder.buildAlert(subscriber, sensor);
            try {
                mailSender.send(alert);
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            }
        }
    }

    /**
     * The getReport method is used to sent report e-mails to a subscriber of the given sensor. Reports contain
     * relevant information about the sensor and the data collected.
     * The method uses the SensorDAO class to retrieve the information about the sensor and its data. The e-mail is
     * then built and sent using the MailBuilder and MailSender classes, respectively.
     * The method is only called by the CheckerUtil class.
     * @param mailAddress e-mail address of the subscriber the report is sent to.
     * @param sensorID ID of the sensor the report is about.
     */
    public void sendReport(String mailAddress, String sensorID) throws JSONException {

        Sensor sensor = sensorDAO.get(sensorID);
        Subscription subscription = subscriptionDAO.get(mailAddress, sensorID);

        long reportInterval = subscription.getReportInterval();
        LocalDate timeframeStart = LocalDate.now().minusDays(reportInterval);
        sensorDAO.setStats(sensor, timeframeStart);


        //Build and send mail
        Report report = mailBuilder.buildReport(mailAddress, sensor);
        try {
            mailSender.send(report);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
