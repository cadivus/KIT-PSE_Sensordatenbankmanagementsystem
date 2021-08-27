package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.*;

import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@SpringBootTest
class ReplayServiceImpTest {

  @Autowired
  ReplayServiceImp replayServiceImp;
  @MockBean
  ObservationRepository observationRepository;
  @MockBean
  DatastreamRepository datastreamRepository;
  @SpyBean
  ProxyHelper proxyHelper;
  @Test
    //@Disabled("Until we can figure out how to create a database connection on the github")
  void createNewDataStream() {

    Requests requests = new Requests();
    requests.setStart(LocalDateTime.now());
    requests.setEnd(requests.getStart());
    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () ->
        replayServiceImp.createNewDataStream(requests));

    if(!e.getMessage().contains("Connection to the database failed"))
      assertEquals("Neither information, nor start, nor end, nor sensors can be empty",
          e.getMessage());
    requests.setSensors(List.of("test"));
    if(!e.getMessage().contains("Connection to the database failed"))
      e = Assertions.assertThrows(IllegalArgumentException.class, () ->
          replayServiceImp.createNewDataStream(requests));
    assertEquals("Start and end can not be the same time", e.getMessage());
    Mockito.doCallRealMethod().when(proxyHelper).sseHelper(null, requests, new SseEmitter());
    requests.setEnd(LocalDateTime.now());
    replayServiceImp.createNewDataStream(requests);

  }

  @Test
  void createReplay() {
  }

  @Test
  void getDataStream() {
  }

  @Test
  void destroyDataStream() {
  }
}