package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.BadSortingTypeStringException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.NoSuchSortException;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import edu.teco.sensordatenbankmanagementsystem.util.WriteCsvToResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/**
 * The ObservationController is the entry point for http requests for {@link Observation}s. Methods
 * of this class map to different requests about Observations
 */
@RequestMapping("/observation")
@EnableWebMvc
@CommonsLog
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

  @GetMapping("/observations/{id}")
  public List<Observation> getObservationsBySensorId(
      @PathVariable(name = "id") String sensorId,
      @RequestParam(name = "limit", defaultValue = "0xfF") int limit,
      @RequestParam(name = "sort", defaultValue = "date-dsc") String sort,
      @RequestParam(name = "type", required = false) String type
  ) {
    return observationService.getObservationsBySensorId(sensorId, limit, getSorting(sort), type);
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
  @GetMapping(value = {"/Export/{id}", "/Export/{id}/{start}", "/Export/{id}/{start}/{end}"})
  public void exportToCSV(@PathVariable String id,
      @PathVariable(required = false) LocalDateTime start,
      @PathVariable(required = false) LocalDateTime end, HttpServletResponse response)
      throws IOException {
    response.setContentType("text/csv");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
    response.setHeader(headerKey, headerValue);

    //TODO: Overload these methods instead of using useless start and end points
    Stream<Observation> list = observationService
        .getObservationByDatastream(sensorService.getDatastreams(List.of(id), start, end), start,
            end);

    WriteCsvToResponse.writeObservation(response.getWriter(), list);

  }

  /**
   * Sorting type string composed of two components: sorting criteria (A), sorting order (B) Sorting
   * type string has to be provided in the format "A-B" A: date, value B: asc, dsc
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
