package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.services.ReplayService;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping("/observation")
@EnableWebMvc
@CommonsLog
@ResponseBody
@Controller
public class ReplayController {


  private DateTimeFormatter DATE_FORMAT;

  public ReplayController(
      ReplayService replayService) {
    this.replayService = replayService;
  }

  @Value("${globals.date_format}")
  private void setDATE_FORMAT(String pattern, String b){
    DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
  }


  public final ReplayService replayService;
  @Autowired
  SensorService sensorService;

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
    return replayService.createNewDataStream(data).toString();
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
    return replayService.getDataStream(uuid);
  }

}
