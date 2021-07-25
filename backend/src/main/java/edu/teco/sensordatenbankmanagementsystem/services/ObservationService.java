package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * The ObservationService provides more complex methods for functionality concerning the querying of {@link Observation} data based on the repositories.
 */
public interface ObservationService {
    /**
     * This method creates a new SSEEmitter DataStream using the information provided in the parameter as well as a
     * repository
     * @param information This should contain the specific information about the Datastream that is to be created.
     *                    At least sensor, interval and start date need to be in here
     * @return The UUID of the newly created Datastream
     */
    UUID createNewDataStream(Requests information);

    /**
     * This returns a single Observation Model from the Repository
     * @param id The ID of the Observation
     * @return
     */
    Observation getObservation(String id);
  
    List<Observation> getObservationByDatastream(Datastream id, LocalDateTime start, LocalDateTime end);

    /**
     * This will create a replay of one or more Sensors. It will work akin to the {@link #createNewDataStream(Requests)} but with
     * live data opposed to using already existing data
     * @param information This should contain the Sensor Information for the replay
     * @return The UUID under which the Replay is to be reached
     */
    UUID createReplay(Requests information);

    /**
     * This will return a previously created Datastream from its specified UUID.
     * If there is no DataStream under the given UUID, none will be created
     * @param id The UUID of the Datastream
     * @return An {@link org.springframework.web.servlet.mvc.method.annotation.SseEmitter}
     */
    SseEmitter getDataStream(UUID id);

    /**
     * This will delete the Datastream from the database and will make it send its closing message
     * @param id The UUID of the Datastream
     */
    void destroyDataStream(UUID id);

    List<Observation> getObservationsBySensorId(String sensorId, int limit, String sort, String filter);
}

