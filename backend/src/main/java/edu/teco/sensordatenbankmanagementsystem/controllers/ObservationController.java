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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

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
    public List<Observation> getObservationsBySensorId(@PathVariable String sensorUUID){
        return null;
    }


    @GetMapping("/Export/{id}")
    public void exportToVSC(@PathVariable String id, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        //TODO: This is incorrect
        List<Observation> list = List.of(observationService.getObservation(id));

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "E-mail", "Full Name", "Roles", "Enabled"};
        String[] nameMapping = {"id", "email", "fullName", "roles", "enabled"};

        csvWriter.writeHeader(csvHeader);

        for (Observation observation : list) {
            csvWriter.write(observation, nameMapping);
        }

        csvWriter.close();

    }

}
