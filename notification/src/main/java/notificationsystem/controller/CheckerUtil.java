package notificationsystem.controller;

import lombok.extern.apachecommons.CommonsLog;
import notificationsystem.model.Sensor;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * An instance of the CheckerUtil class always runs in the background. Its purpose is to periodically check if alerts
 * or reports have to be sent by the system.
 */
@CommonsLog
@Component
public class CheckerUtil {

    @Value("${sensors.backend.url}")
    private String backendUrl;
    private final static String LOG_ALERT = "Checking for sensor-failure";
    private final static String LOG_REPORT = "Checking for reports";
    private final static String LOG_ERROR = "Failed to get data from backend API";
    private final String checkActiveUrl;
    //Sends alert if a sensor has been inactive for three days.
    private final static int INACTIVE_DAYS_THRESHOLD = 3;
    private final static int SENSOR_ACTIVE = 1;
    private final static int SENSOR_INACTIVE = -1;

    private final Controller controller;
    private final SubscriptionDAO subscriptionDAO;
    private final SensorDAO sensorDAO;
    private final RestTemplate restTemplate;
    private Map<String, Integer> sensorActiveDict;

    @Autowired
    public CheckerUtil(Controller controller, SubscriptionDAO subscriptionDAO, SensorDAO sensorDAO, RestTemplate restTemplate) {
        this.controller = controller;
        this.subscriptionDAO = subscriptionDAO;
        this.sensorDAO = sensorDAO;
        this.restTemplate = restTemplate;
        this.sensorActiveDict = new HashMap<>();
        this.checkActiveUrl = backendUrl + "/active";
    }

    /**
     * Periodically checks if a sensor has malfunctioned by communicating with the project API.
     * If one or multiple sensor failures occurred, the method calls the controller to send an alert to the subscribers
     * of these sensors.
     */
    @Scheduled(fixedRate = 500000)
    public void checkForSensorFailure() {
        //Prepare sensor ids
        log.info(LOG_ALERT);
        List<Sensor> sensors = sensorDAO.getAll();
        LinkedList<String> sensorIds = new LinkedList<>();

        for(Sensor sensor : sensors) {
            sensorIds.add(sensor.getId());
        }

        //Get information about sensor activity and store it with the correlating sensor id
        Integer[] response = restTemplate.getForObject(checkActiveUrl, Integer[].class, sensorIds, INACTIVE_DAYS_THRESHOLD);
        List<Integer> sensorStatus;
        if (response != null) {
            sensorStatus = Arrays.asList(response);
        } else {
            log.info(LOG_ERROR);
            return;
        }
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
            if (sensorActiveDict.get(id).equals(SENSOR_ACTIVE) && sensorCurrentlyActiveDict.get(id).equals(SENSOR_INACTIVE)) {
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
    public void checkForReports() throws JSONException {
        log.info(LOG_REPORT);
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
