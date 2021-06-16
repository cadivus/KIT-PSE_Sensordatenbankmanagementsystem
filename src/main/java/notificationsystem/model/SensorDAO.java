package notificationsystem.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SensorDAO implements DAO<Sensor> {

    @Override
    public Sensor get(Sensor sensor) {
        return null;
    }

    public Sensor get(UUID sensorID) {
        return null;
    }

    @Override
    public List<Sensor> getAll() {
        return null;
    }

    @Override
    public void save(Sensor sensor) {

    }

    @Override
    public void delete(Sensor sensor) {

    }

}
