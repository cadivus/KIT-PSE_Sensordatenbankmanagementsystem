package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationServiceImp;
import lombok.extern.apachecommons.CommonsLog;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;


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

    @GetMapping("/allobservations/{id}")
    public List<Observation> getObservationsBySensorId(UUID sensorUUID){
        return null;
    }
}