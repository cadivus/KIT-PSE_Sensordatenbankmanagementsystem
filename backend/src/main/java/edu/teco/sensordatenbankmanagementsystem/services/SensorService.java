package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.Interpolator;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The SensorService provides more complex methods for functionality concerning the querying of {@link Thing} data based on the repositories.
 */
public interface SensorService {
    /**
     * This creates the new Metadata for any given Thing
     */
    void createNewMetaData();

    /**
     * This returns a single Thing by its ID
     * @param id The UUID of the Thing to be returned
     */
    Thing getSensor(String id);

    /**
     * This gets the MetaData of a single Thing using either the Id of the Thing or of the Metadata
     * @param id The UUID of the Meta Data or the Thing
     */
    Thing getSensorMetaData(String id);

    Datastream getDatastream(String sensor_id);

    Datastream getDatastream(String sensor_id, LocalDateTime start, LocalDateTime end);

    List<Thing> getAllSensors();

    RenderedImage getGraphImageOfThing(String id, String obsId, LocalDateTime frameStart, LocalDateTime frameEnd,
                                        int maxInterPoints, Dimension imageDimension, int granularity,
                                        Interpolator<Double, Double> interpolator);

}
