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


    /**
     * This tries to return a List of all Observations belonging to a specific Thing
     * There are various limitations that can be turned on to reduce processing time.
     * In 1 month there can be easily more than 100 000 results.
     * @param thingId The 'things' unique identifier
     * @param limit The maximum amount of results to be returned
     * @param sort The sorting function in which the results are to be sorted
     * @param filter
     * @param frameStart The start time
     * @param frameEnd The end time
     * @return A list of observations
     */
    List<Observation> getObservationsByThingId(String thingId, int limit, Sort sort, List<String> filter, LocalDateTime frameStart, LocalDateTime frameEnd);

    /**
     *
     * @return the list of all Observer properties in the database
     */
    List<ObservedProperty> getAllObservedProperties();

    /**
     * Gets all observations belonging to a specific Datastream
     * @param datastreamId The datastreams unique identifier
     * @param page A Pageable limits the amount of results to a specific amount of pages
     * @return A Stream of Observations
     */
    Stream<Observation> getObservationByDatastreamId(String datastreamId, Pageable page);

}

