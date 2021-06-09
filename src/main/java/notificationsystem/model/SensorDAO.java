package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public class SensorDAO implements DAO<Sensor> {


    public String getSensorData() {
        return null;
    }

    public List<String> getSubscribers() {
        return null;
    }

    public String getFailurerate() {
        return null;
    }

    public String getLocation() {
        return null;
    }

    public List<String> getCategories() {
        return null;
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
