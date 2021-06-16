package notificationsystem.model;

import notificationsystem.controller.MailAddress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SubscriptionDAO implements DAO<Subscription> {
    @Override
    public Subscription get(long id) {
        return null;
    }

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

    @Override
    public void save(Subscription subscription) {

    }

    @Override
    public void delete(Subscription subscription) {

    }


}
