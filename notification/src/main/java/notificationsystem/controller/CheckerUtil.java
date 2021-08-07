package notificationsystem.controller;

import lombok.extern.apachecommons.CommonsLog;
import notificationsystem.model.Sensor;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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
    private final SensorDAO sensorDAO;
    private final RestTemplate restTemplate;
    private Map<String, Integer> sensorActiveDict;
    private final static int INACTIVE_DAYS_THRESHOLD = 3;

    @Autowired
    public CheckerUtil(Controller controller, SubscriptionDAO subscriptionDAO, SensorDAO sensorDAO, RestTemplate restTemplate) {
        this.controller = controller;
        this.subscriptionDAO = subscriptionDAO;
        this.sensorDAO = sensorDAO;
        this.restTemplate = restTemplate;
        this.sensorActiveDict = new HashMap<>();
    }

    /**
     * Periodically checks if a sensor has malfunctioned by communicating with the project API.
     * If one or multiple sensor failures occurred, the method calls the controller to send an alert to the subscribers
     * of these sensors.
     */
    @Scheduled(fixedRate = 500000)
    public void checkForSensorFailure() {
        //Prepare sensor ids
        log.info("Checking for sensor-failure");
        List<Sensor> sensors = sensorDAO.getAll();
        LinkedList<String> sensorIds = new LinkedList<>();

        for(Sensor sensor : sensors) {
            sensorIds.add(sensor.getId());
        }

        //Get information about sensor activity and store it with the correlating sensor id
        //TODO: Richtige URL
        Integer[] response = restTemplate.getForObject("/active", Integer[].class, sensorIds, INACTIVE_DAYS_THRESHOLD);
        List<Integer> sensorStatus = Arrays.asList(response);
        Map<String, Integer> sensorCurrentlyActiveDict = new HashMap<>();

        if(sensorActiveDict.isEmpty()) {
        for(int i = 0; i < sensorIds.size(); i++) {
            sensorActiveDict.put(sensorIds.get(i), sensorStatus.get(i));
            }
        }
        for(int i = 0; i < sensorIds.size(); i++) {
            sensorCurrentlyActiveDict.put(sensorIds.get(i), sensorStatus.get(i));
        }

        //Check which sensors which were active are currently inactive
        for(String id : sensorIds) {
            if (sensorActiveDict.get(id).equals(1) && sensorCurrentlyActiveDict.get(id).equals(0)) {
                controller.sendAlert(id);
            }
        }

        //Update dictionary
        sensorActiveDict = sensorCurrentlyActiveDict;
    }

    /**
     * Keeps track of the reports which have to be periodically sent to subscribers of sensors and the time intervals
     * between these reports.
     * Calls the controller to send such a report to a subscriber if necessary.
     */
    @Scheduled(fixedRate = 500000)
    public void checkForReports() {
        log.info("Starting update process");
        LinkedList<Subscription> subs = new LinkedList<>(subscriptionDAO.getAll());

        //Check if a report has to be sent
        for (Subscription subscription : subs) {
            Period period = Period.between(subscription.getSubTime().plusDays(subscription.getReportInterval()), LocalDate.now());
            if (!period.isNegative()) {
                //Send report and update timestamp
                controller.sendReport(subscription.getSubscriberAddress(), subscription.getSensorId());
                subscription.setSubTime(subscription.getSubTime().plusDays(subscription.getReportInterval()));
            }
        }

    }
}
