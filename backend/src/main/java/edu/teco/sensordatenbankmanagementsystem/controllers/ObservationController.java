package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.BadSortingTypeStringException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.NoSuchSortException;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import edu.teco.sensordatenbankmanagementsystem.util.WriteCsvToResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The ObservationController is the entry point for http requests for {@link Observation}s. Methods
 * of this class map to different requests about Observations
 */
@RequestMapping("/observation")
@EnableWebMvc
@CommonsLog
@ResponseBody
@RestController
public class ObservationController {

    private DateTimeFormatter DATE_FORMAT;
    @Value("${globals.date_format}")
    private void setDATE_FORMAT(String pattern, String b){
        DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
    }


    public final ObservationService observationService;
    @Autowired
    SensorService sensorService;

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
    }

    /**
     * Maps a post request that creates a new SSE stream
     *
     * @return UUID of the created SSE stream
     */
  @ResponseBody
  @PostMapping(value = "/newSSE", consumes = "application/json", produces = "text/plain")
  public String createNewSse(@RequestBody Requests data) {
    if (data.getSpeed() == 0) {
      data.setSpeed(1);
    }
    log.info("received Datastream request");
    return observationService.createNewDataStream(data).toString();
  }

    /**
     * Maps a get request that gets the SSE stream with the given UUID
     *
     * @param id UUID of SSE stream to get
     * @return SSE stream for the given UUID
     */
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @GetMapping("/stream/{id}")
  public SseEmitter streamSseMvc(@PathVariable String id) {
    log.info("request for outgoing stream for id: " + id);
    UUID uuid = UUID.fromString(id);
    return observationService.getDataStream(uuid);
  }

    @GetMapping("/getAllObs")
    public List<ObservedProperty> getAllObservedProperties(){
        return observationService.getAllObservedProperties();
    }

    /**
     * Gets all observations of the given thing
     * @param thingId of the thing to get the observations of
     * @param limit maximum amount to get
     * @param sort sort by what (see {@link #getSorting(String)for} for more
     * @param obsIds whether or not to limit fetched data to a certain observed type
     * @param frameStart start of time frame to fetch from
     * @param frameEnd end of time frame to fetch from
     * @return list of observations according to the above criteria
     */
    @GetMapping("/observations/{id}")
    public List<Observation> getObservationsBySensorId(
            @PathVariable(name = "id") String thingId,
            @RequestParam(name = "limit", defaultValue = "0xfF") int limit,
            @RequestParam(name = "sort", defaultValue = "date-dsc") String sort,
            @RequestParam(name = "obsIds", required = false) List<String> obsIds,
            @RequestParam(name = "frameStart", defaultValue = "0001-01-01") String frameStart,
            @RequestParam(name = "frameEnd", required = false) String frameEnd
    ) {
        return observationService.getObservationsByThingId(
                thingId,
                limit,
                getSorting(sort),
                obsIds,
                LocalDate.parse(frameStart, DATE_FORMAT).atStartOfDay(),
                Optional.ofNullable(frameEnd)
                        .map(s->LocalDate.parse(frameEnd, DATE_FORMAT))
                        .orElseGet(LocalDate::now)
                        .atStartOfDay()
        );
    }

    /**
     * This is the entry point for Csv exports
     *
     * @param id       This is the Sensor ID for which the observations should be exported
     * @param start    The (Optional) start date
     * @param end      The (Optional) End date
     * @param response The HttpServlet in which the result should be written
     * @throws IOException If there is no way to write to the @param response
     */
    @Transactional
    @GetMapping(value = {"/Export/{id}", "/Export/{id}/{start}", "/Export/{id}/{start}/{end}"})
    public void exportToCSV(@PathVariable String id,
                            @PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss") LocalDateTime start,
                            @PathVariable(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss")  LocalDateTime end, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        //TODO: Overload these methods instead of using useless start and end points
        List<Observation> list = observationService
                .getObservationByDatastream(sensorService.getDatastreams(List.of(id), start, end), start, end);

        WriteCsvToResponse.writeObservation(response.getWriter(), list);

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
        if (sortingInfo.length != 2) {
            throw new BadSortingTypeStringException();
        }
        Sort sort = switch (sortingInfo[0]) {
            case "date" -> Sort.by("phenomenonStart", "phenomenonEnd");
            case "value" -> Sort.by("resultNumber");
            default -> throw new NoSuchSortException();
        };
        return sortingInfo[1].equals("dsc") ? sort.descending() : sort.ascending();
    }

}
