package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
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

    /**
     * This gets the MetaData of a single Sensor using either the Id of the Sensor or of the Metadata
     * @param id The UUID of the Meta Data or the Sensor
     */
    Sensor getSensorMetaData(String id);

    Datastream getDatastream(String sensor_id);

    Datastream getDatastream(String sensor_id, LocalDateTime start, LocalDateTime end);

    List<Sensor> getAllSensors();

    RenderedImage getGraphImageOfThing(String id, String obsId, LocalDateTime frameStart, LocalDateTime frameEnd,
                                       int maxInterPoints, Dimension imageDimension, int granularity);
}
