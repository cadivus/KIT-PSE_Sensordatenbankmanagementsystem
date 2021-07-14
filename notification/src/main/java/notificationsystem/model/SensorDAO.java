package notificationsystem.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The SensorDAO class implements the DAO interface to handle database queries regarding sensors. For that end it
 * provides get, save, delete and getAll methods designed to hide the actual database queries, offering a single
 * access point to all sensor related data and information.
 */
public class SensorDAO implements DAO<Sensor> {

    @Override
    public Optional<Sensor> get(Sensor sensor) {
        return null;
    }

    /**
     * Gets a sensor specified by its unique ID from the database.
     * @param sensorID ID of the sensor to be fetched.
     * @return The sensor with the given ID.
     */
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
