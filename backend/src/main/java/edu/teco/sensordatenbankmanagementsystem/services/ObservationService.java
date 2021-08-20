package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import java.util.stream.Stream;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ObservationService provides more complex methods for functionality concerning the querying of {@link Observation} data based on the repositories.
 */
public interface ObservationService {



    /**
     * This returns a single Observation Model from the Repository
     *
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



    List<Observation> getObservationsByThingId(String thingId, int limit, Sort sort, List<String> filter, LocalDateTime frameStart, LocalDateTime frameEnd);

    List<ObservedProperty> getAllObservedProperties();

    Stream<Observation> getObservationByDatastreamId(String datastreamId, Pageable page);

}

