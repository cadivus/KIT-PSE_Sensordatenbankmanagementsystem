package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public class SubscriptionDAO implements DAO<Subscription> {
    @Override
    public Optional<Subscription> get(long id) {
        return Optional.empty();
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
