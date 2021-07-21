package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;

import java.util.List;
import java.util.UUID;

/**
 * The SensorService provides more complex methods for functionality concerning the querying of {@link Sensor} data based on the repositories.
 */
public interface SensorService {
    /**
     * This creates the new Metadata for any given Sensor
     */
    void createNewMetaData();

    /**
     * This returns a single Sensor by its ID
     * @param id The UUID of the Sensor to be returned
     */
    Sensor getSensor(String id);

    List<Sensor> getAllSensors();

}
