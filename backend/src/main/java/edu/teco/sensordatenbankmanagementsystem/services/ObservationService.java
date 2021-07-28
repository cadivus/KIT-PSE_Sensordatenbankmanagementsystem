package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

/**
 * The ObservationService provides more complex methods for functionality concerning the querying of {@link Observation} data based on the repositories.
 */
public interface ObservationService {
    /**
     * This method creates a new SSEEmitter DataStream using the information provided in the parameter
     * The emitter will be put in an asynchronous executor and put into a Bidirectional Hashmap
     * for storage
     * @param information This should contain the specific information about the Datastream that is to be created.
     *                    At least sensor(s), speed and start date need to be in here
     * @return The UUID of the newly created Datastream
     */
    UUID createNewDataStream(Requests information);

    /**
     * This returns a single Observation Model from the Repository
     * @param id The ID of the Observation
     * @return
     */
    @Deprecated
    Observation getObservation(String id);

    /**
     * This will take a stream of datastreams and try to find all Observations between the start
     * and end date which are in these datastreams and will return them as a Stream
     * @param datastreams A datastream is part of the Frost Database by Teco. This receives a stream
     *                    of those
     * @param start The earliest date it should be looking for
     * @param end The date after which no observations should be returned
     * @return A Stream of Observations
     */
    Stream<Observation> getObservationByDatastream(Stream<Datastream> datastreams, LocalDateTime start, LocalDateTime end);

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
    @Deprecated
    void destroyDataStream(UUID id);


    List<Observation> getObservationsBySensorId(String sensorId, int limit, Sort sort, String filter);
}

