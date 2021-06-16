package notificationsystem.controller;

import notificationsystem.model.SensorDAO;
import notificationsystem.model.SensorName;
import notificationsystem.model.SubscriberDAO;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class SubscriptionManager {

    public void addSubscription(MailAddress mailAddress, SensorName sensorName, LocalDateTime subTime) {
        SubscriberDAO subscriberDAO;
        SensorDAO sensorDAO;
    }

    public void deleteSubscription(MailAddress mailAddress, SensorName sensorName) {
        SubscriberDAO subscriberDAO;
        SensorDAO sensorDAO;
    }

}
