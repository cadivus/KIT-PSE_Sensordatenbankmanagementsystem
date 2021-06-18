package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import org.jooq.JSON;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

/**
 * The {@code ObservationService} provides more complex methods for functionality concerning the querying of {@code Observation} data based on the repositories.
 */
public interface ObservationService {
    public UUID createNewDataStream(JSON information);

    public Observation getObservation(Long id);

    public UUID createReplay(JSON information);

    public SseEmitter getDataStream(UUID id);

    public void destroyDataStream(UUID id);
}
