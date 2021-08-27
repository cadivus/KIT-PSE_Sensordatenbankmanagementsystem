package notificationsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The SubscriptionDAO class implements the DAO interface to handle database queries regarding subscriptions.
 * It provides basic methods such as get, save, delete and getAll, as well as methods to get all subscribers to a
 * given sensor or all sensor a given user is subscribed to. This allows the SubscriptionDAO class to handle all
 * database requests regarding subscriptions.
 * In the context of the E-Mail Notification System, these are mainly needed for alert and report mails, as well as
 * the direct management of subscriptions for the website.
 */
@Service
public class SubscriptionDAO implements DAO<Subscription>{

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionDAO(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    @Override
    public Optional<Subscription> get(Subscription subscription) {
        return subscriptionRepository.findById(subscription.getId());
    }

    /**
     * Gets a subscription from the database based of a specified e-mail and sensor ID.
     * @param mailAddress e-mail address of the subscriber.
     * @param sensorID ID of the sensor the user is subscribed to.
     * @return subscription of the user with the specified e-mail address to the sensor with the given ID,
     * if it exists.
     */
    public Subscription get(String mailAddress, String sensorID) {
        List<Subscription> allSubs = subscriptionRepository.findAll();
        for (Subscription sub : allSubs) {
            if (sub.getSubscriberAddress().equals(mailAddress) && sub.getSensorId().equals(sensorID)) {
                return sub;
            }
        }
        return null;
    }

    @Override
    public List<Subscription> getAll() {
        return new ArrayList<>(subscriptionRepository.findAll());
    }

    /**
     * Gets a list of all e-mail addresses of users subscribed to a sensor. The sensor ID is used to identify
     * the sensor of which the subscribers are to be fetched.
     * @param sensorID ID of the sensor the users are subscribed to.
     * @return List of e-mail addresses of all the users subscribed to the sensor with the given ID.
     */
    public List<String> getAllSubscribers(String sensorID) {
        List<String> subscribers = new ArrayList<>();
        List<Subscription> allSubs = subscriptionRepository.findAll();
        for (Subscription sub : allSubs) {
            if (sub.getSensorId().equals(sensorID)) {
                subscribers.add(sub.getSubscriberAddress());
            }
        }
        return subscribers;
    }

    /**
     * Gets a list of all sensors the user with the given e-mail address is subscribed to.
     * @param mailAddress e-mail address of the user.
     * @return List of sensor IDs of all the sensors the user with the given e-mail address is currently
     * subscribed to.
     */
    public List<String> getAllSensors(String mailAddress) {
        List<String> sensors = new ArrayList<>();
        List<Subscription> allSubs = subscriptionRepository.findAll();
        for (Subscription sub : allSubs) {
            if (sub.getSubscriberAddress().equals(mailAddress)) {
                sensors.add(sub.getSensorId());
            }
        }
        return sensors;
    }

    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void delete(Subscription subscription) {
        subscriptionRepository.delete(subscription);
    }


}
