package notificationsystem.controller;

import notificationsystem.model.SensorDAO;
import notificationsystem.model.SensorName;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

public class SubscriptionManager {

    public void addSubscription(Subscription subscription) {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        subscriptionDAO.save(subscription);
    }

    public void deleteSubscription(MailAddress mailAddress, UUID sensorID) {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        Subscription toDelete = subscriptionDAO.get(mailAddress, sensorID);
        subscriptionDAO.delete(toDelete);
    }

}
