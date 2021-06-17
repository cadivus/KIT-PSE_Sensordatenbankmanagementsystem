package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import org.jooq.JSON;

import java.util.UUID;

/**
 * The {@code ObservationService} provides more complex methods for functionality concerning the querying of {@code Observation} data based on the repositories.
 */
public interface ObservationService {
    public UUID createNewDataStream(JSON information);

    public Observation getObservation(Long id);
}
