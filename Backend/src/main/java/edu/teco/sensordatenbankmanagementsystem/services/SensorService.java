package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;

/**
 * The {@code SensorService} provides more complex methods for functionality concerning the querying of {@code Sensor} data based on the repositories.
 */
public interface SensorService {

    public Sensor getSensor(long id);

    public Sensor getSensorMetaData(long id);

}
