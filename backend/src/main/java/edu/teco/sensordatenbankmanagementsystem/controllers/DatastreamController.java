package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.services.DatastreamService;
import edu.teco.sensordatenbankmanagementsystem.services.DatastreamServiceImp;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationServiceImp;
import edu.teco.sensordatenbankmanagementsystem.services.SensorServiceImp;
import edu.teco.sensordatenbankmanagementsystem.services.ThingService;
import edu.teco.sensordatenbankmanagementsystem.services.ThingServiceImp;
import edu.teco.sensordatenbankmanagementsystem.util.WriteCsvToResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the entry point for all datastream related API calls.
 */
@RestController
@RequestMapping("/datastream")
public class DatastreamController {

  private final ThingService thingService;
  private final ObservationService observationService;
  private final DatastreamService datastreamService;

  public DatastreamController(
      ThingServiceImp thingService, SensorServiceImp sensorService,
      ObservationServiceImp observationService,
      DatastreamServiceImp datastreamService) {
    this.thingService = thingService;
    this.observationService = observationService;
    this.datastreamService = datastreamService;
  }

  /**
   * This will return all Datastreams belonging to a specific thing
   * @param thingId The Things unique identifier
   * @return A List of Datastreams. The Observations are excluded from this list due to size issues
   */
  @GetMapping("/listDatastreams")
  @Transactional
  public List<Datastream> getDatastreams(@RequestParam(value = "id") String thingId) {
    return datastreamService.getDatastreamsByThing(thingId);
  }

  /**
   * This adds the possibility to export Observations belonging to a specific datastreams as a CSV
   * file
   * @param datastreamId The datastreams unique identifier
   * @param limitStr The maximum amount of Observations expressed as a simple Integer
   * @param response The HTTPServlet response is given to this function by the frontcontroller
   * @throws IOException If the function is unable to write to the output
   */
  @GetMapping(value = "/export", params = "limit")
  @Transactional
  public void exportToCsv(@RequestParam(value = "id") String datastreamId,
      @RequestParam(value = "limit", required = false) String limitStr,
      HttpServletResponse response)
      throws IOException {
    response.setContentType("text/csv");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());
    int limit = Integer.MAX_VALUE;
    if (limitStr != null) {
      try {
        limit = Integer.parseInt(limitStr);
      } catch (NumberFormatException ex) {
        throw new IllegalArgumentException("Limit has to be a number");
      }
    }

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=observations_" + currentDateTime + ".csv";
    response.setHeader(headerKey, headerValue);
    Stream<Observation> list = observationService.getObservationByDatastreamId(datastreamId,
        PageRequest.of(0, limit));

    WriteCsvToResponse.writeObservation(response.getWriter(), list);

  }

  /**
   * This adds the possibility to export Observations belonging to a specific datastreams as a CSV
   * file
   * @param datastreamId The datastreams unique identifier
   * @param start The start point of the request. The function will only check for observations
   *              after this date
   * @param end The end point of the request. The function will only check for observations
   *            before this date
   * @param response The HTTPServlet response is given to this function by the frontcontroller
   * @throws IOException If the function is unable to write to the output
   */
  @Transactional
  @GetMapping(value = "/export", params = {"start", "end"})
  public void exportToCsv(@RequestParam(value = "id") String datastreamId,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss") LocalDateTime start,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss") LocalDateTime end,
      HttpServletResponse response) throws IOException {
    response.setContentType("text/csv");

    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=observations_" + currentDateTime + ".csv";
    response.setHeader(headerKey, headerValue);

    Stream<Observation> list = observationService
        .getObservationByDatastream(Stream.of(datastreamService.getDatastream(datastreamId)), start, end);

    WriteCsvToResponse.writeObservation(response.getWriter(), list);
  }

  /**
   * This exports a datastream by its Id
   * @param datastreamId the datastreams unique identifier
   * @return A Datastream which will be formatted as a JSON
   */
  @GetMapping("/getDatastream")
  public Datastream exportDatastream(@RequestParam(value = "id") String datastreamId) {
    return datastreamService.getDatastream(datastreamId);
  }

}



