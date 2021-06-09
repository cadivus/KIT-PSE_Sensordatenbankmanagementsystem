package notificationsystem.model;

import java.util.List;
import java.util.Optional;

public class SensorDAO implements DAO<Sensor> {

    @Override
    public Optional<Sensor> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Sensor> getAll() {
        return null;
    }

}
