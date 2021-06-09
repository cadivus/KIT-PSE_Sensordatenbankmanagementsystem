package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public class SubscriberDAO implements DAO<Subscriber> {

    @Override
    public Optional<Subscriber> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Subscriber> getAll() {
        return null;
    }

    public void deleteSubscriber(Subscriber subscriber) {

    }

    public void updateSubscriber(Subscriber subscriber) {

    }
}
