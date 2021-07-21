package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import edu.teco.sensordatenbankmanagementsystem.util.WriteCsvToResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.extern.apachecommons.CommonsLog;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public final ObservationService observationService;
    public final SensorService sensorService;
    /**
     * Instantiates a new Observation controller.
     *
     * @param observationService the observation service which handles the underlying business logic
     *                           The Autowired Annotation automatically injects a Spring bean
     * @param sensorService
     */
    @Autowired
    public ObservationController(ObservationService observationService,
        SensorService sensorService) {
        this.observationService = observationService;
        this.sensorService = sensorService;
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
    public List<Observation> getObservationsBySensorId(@PathVariable String id){
        return new ArrayList<Observation>();
    }


    @GetMapping(value = {"/Export/{id}", "/Export/{id}/{start}","/Export/{id}/{start}/{end}"})
    public void exportToVSC(@PathVariable String id, @PathVariable(required = false) LocalDateTime start,@PathVariable(required = false) LocalDateTime end, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        if (null == start)
            start = LocalDateTime.of(1900,1,1,0,0);
        if (null == end)
            end = LocalDateTime.now();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        //TODO: Overload these methods instead of using useless start and end points
       List<Observation> list = observationService.getObservationByDatastream(sensorService.getDatastream(id, start, end), start, end);

        WriteCsvToResponse.writeObservation(response.getWriter(), list);

    }

}
