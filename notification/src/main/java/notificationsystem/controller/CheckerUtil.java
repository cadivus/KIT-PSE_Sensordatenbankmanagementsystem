package notificationsystem.controller;

import lombok.extern.apachecommons.CommonsLog;
import notificationsystem.model.Sensor;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * An instance of the CheckerUtil class always runs in the background. Its purpose is to regulary check if alerts
 * or reports have to be sent by the system. The class is designed with the singleton-pattern as only a single
 * instance should, for example, issue reports.
 */
@CommonsLog
@Component
public class CheckerUtil {

    private final Controller controller;
    private final SubscriptionDAO subscriptionDAO;

    @Autowired
    public CheckerUtil(Controller controller, SubscriptionDAO subscriptionDAO) {
        this.controller = controller;
        this.subscriptionDAO = subscriptionDAO;
    }

    /**
     * Periodically checks if a sensor has malfunctioned by communicating with the project API.
     * If one or multiple sensor failures occurred, the method calls the controller to send an alert to the subscribers
     * of these sensors.
     */
    @Scheduled(fixedRate = 500000)
    public void checkForSensorFailure() {

    }

    /**
     * Keeps track of the reports which have to be periodically sent to subscribers of sensors and the time intervals
     * between these reports.
     * Calls the controller to send such a report to a subscriber if necessary.
     */
    @Scheduled(fixedRate = 500000)
    public void checkForReports() {
        log.info("Starting update process");
        LinkedList<Subscription> subs = new LinkedList<Subscription>(subscriptionDAO.getAll());

        //Check if a report has to be sent
        for (Subscription subscription : subs) {
            Period period = Period.between(subscription.getSubTime().plusDays(subscription.getReportInterval()), LocalDate.now());
            if (!period.isNegative()) {
                //Send report and update timestamp
                controller.sendReport(subscription.getSubscriberAddress(), subscription.getSensor());
                subscription.setSubTime(subscription.getSubTime().plusDays(subscription.getReportInterval()));
            }
        }

    }
}
