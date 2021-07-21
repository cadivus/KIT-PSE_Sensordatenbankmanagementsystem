package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.BadSortingTypeStringException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.NoSuchSortException;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationServiceImp;
import lombok.extern.apachecommons.CommonsLog;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;


/**
 * The ObservationController is the entry point for http requests for {@link Observation}s.
 * Methods of this class map to different requests about Observations
 */
@RequestMapping("/observation")
@EnableWebMvc
@CommonsLog
@ResponseBody
@Controller
public class ObservationController {
    ObservationService observationService;
    /**
     * Instantiates a new Observation controller.
     *
     * @param observationService the observation service which handles the underlying business logic
     *                           The Autowired Annotation automatically injects a Spring bean
     */
    @Autowired
    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }
    /**
     * Maps a post request that creates a new SSE stream
     *
     * @return UUID of the created SSE stream
     */
    @PostMapping("/newSSE")
    public UUID createNewSse(@RequestBody Requests data) {
        log.info("received Datastream request");
        return observationService.createNewDataStream(data);
    }
    /**
     * Maps a get request that gets the SSE stream with the given UUID
     *
     * @param id UUID of SSE stream to get
     * @return SSE stream for the given UUID
     */
    @GetMapping("/stream/{id}")
    public SseEmitter streamSseMvc(@PathVariable UUID id) {
        log.info("request for outgoing stream for id: " +id);
        return observationService.getDataStream(id);
    }

    @GetMapping("/observations/{id}")
    public List<Observation> getObservationsBySensorId(
            @PathVariable(name="id") String sensorId,
            @RequestParam(name="limit", defaultValue = "Integer.MAX_VALUE") int limit,
            @RequestParam(name="sort", defaultValue="date-dsc") String sort,
            @RequestParam(name="filter", required = false) String filter
            ) {
        return observationService.getObservationsBySensorId(sensorId, limit, sort, filter);
    }

    /**
     * Sorting type string composed of two components: sorting criteria (A), sorting order (B)
     * Sorting type string has to be provided in the format "A-B"
     * A: date, value
     * B: asc, dsc
     *
     * @param sortingTypeString string describing the type of sorting
     * @return sort of that sorting type
     */
    private Sort getSorting(String sortingTypeString) {
        String[] sortingInfo = sortingTypeString.split("-");
        if(sortingInfo.length != 2) {
            throw new BadSortingTypeStringException();
        }
        Sort r = Sort.by(sortingInfo[0]);
        return sortingInfo[1].equals("dsc") ? r.descending() : r.ascending();
    }

}
