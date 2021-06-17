package notificationsystem.model;

import notificationsystem.controller.MailAddress;

import java.util.List;
import java.util.UUID;

/**
 * The SubscriptionDAO class implements the DAO interface to handle database queries regarding subscriptions.
 * It provides basic methods such as get, save, delete and getAll, as well as methods to get all subscribers to a
 * given sensor or all sensor a given user is subscribed to. This allows the SubscriptionDAO class to handle all
 * database requests regarding subscriptions.
 * In the context of the E-Mail Notification System, these are mainly needed for alert and report mails, as well as
 * the direct management of subscriptions for the website.
 */
public class SubscriptionDAO implements DAO<Subscription> {

    @Override
    public Subscription get(Subscription subscription) {
        return null;
    }

    public Subscription get(MailAddress mailAddress, UUID sensorID) {
        return null;
    }

    @Override
    public List<Subscription> getAll() {
        return null;
    }

    public List<MailAddress> getAllSubscribers(UUID sensorID) {
        return null;
    }

    public List<UUID> getAllSensors(String mailAddress) {
        return null;
    }

    @Override
    public void save(Subscription subscription) {

    }

    @Override
    public void delete(Subscription subscription) {

    }


}
