package notificationsystem.controller;

import notificationsystem.model.SensorDAO;
import notificationsystem.model.SensorName;

import java.time.LocalDateTime;
import java.time.Period;

public class SubscriptionManager {

    public void addSubscription(MailAddress mailAddress, SensorName sensorName, LocalDateTime subTime, Period reportInterval) {
        SensorDAO sensorDAO;
    }

    public void deleteSubscription(MailAddress mailAddress, SensorName sensorName) {
        SensorDAO sensorDAO;
    }

}
