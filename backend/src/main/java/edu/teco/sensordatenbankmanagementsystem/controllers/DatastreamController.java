package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/datastream")
public class DatastreamController {

  private final ThingService thingService;
  private final ObservationService observationService;

  public DatastreamController(
      ThingServiceImp thingService, SensorServiceImp sensorService,
      ObservationServiceImp observationService) {
    this.thingService = thingService;
    this.observationService = observationService;
  }

  @GetMapping("/listDatastreams")
  @Transactional
  public List<Datastream> getDatastreams(@RequestParam(value = "id") String thingId) {
    return thingService.getThing(thingId).getDatastream();
  }

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
        .getObservationByDatastream(Stream.of(thingService.getDatastream(datastreamId)), start, end);

    WriteCsvToResponse.writeObservation(response.getWriter(), list);
  }

  @GetMapping("/getDatastream")
  public Datastream exportDatastream(@RequestParam(value = "id") String datastreamId) {
    return thingService.getDatastream(datastreamId);
  }

}



