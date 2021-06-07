package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public class SensorDAO implements DAO<Sensor> {


    public void getSensorData() {

    }

    public void getSubscribers() {

    }

    public void getFailurerate() {

    }

    public void getLocation() {

    }

    public void getCategories() {

    }

    @Override
    public Optional<Sensor> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Sensor> getAll() {
        return null;
    }
}
