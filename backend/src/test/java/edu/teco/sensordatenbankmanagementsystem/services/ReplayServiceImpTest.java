package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@SpringBootTest
@ActiveProfiles("test")
class ReplayServiceImpTest {

  @Autowired
  ReplayServiceImp replayServiceImp;
  @MockBean
  DatastreamService datastreamService;
  @MockBean
  ObservationRepository observationRepository;
  @MockBean
  DatastreamRepository datastreamRepository;
  @SpyBean
  ProxyHelper proxyHelper;
  @Test
  void createNewDataStream() {

    Requests requests = new Requests();
    requests.setStart(LocalDateTime.now());
    requests.setEnd(requests.getStart());
    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () ->
        replayServiceImp.createNewReplay(requests));

    if(!e.getMessage().contains("Connection to the database failed"))
      assertEquals("Neither information, nor start, nor end, nor sensors can be empty",
          e.getMessage());
    requests.setSensors(List.of("test"));
    if(!e.getMessage().contains("Connection to the database failed"))
      e = Assertions.assertThrows(IllegalArgumentException.class, () ->
          replayServiceImp.createNewReplay(requests));
    assertEquals("Start and end can not be the same time", e.getMessage());
    Mockito.doCallRealMethod().when(proxyHelper).sseHelper(null, requests, new SseEmitter());
    requests.setEnd(LocalDateTime.now());
    replayServiceImp.createNewReplay(requests);
  }

  @Test
  void getDataStream() {
    Requests requests = new Requests();
    requests.setStart(LocalDateTime.now());
    requests.setEnd(requests.getStart());
    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () ->
        replayServiceImp.createNewReplay(requests));

    if(!e.getMessage().contains("Connection to the database failed"))
      assertEquals("Neither information, nor start, nor end, nor sensors can be empty",
          e.getMessage());
    requests.setSensors(List.of("test"));
    if(!e.getMessage().contains("Connection to the database failed"))
      e = Assertions.assertThrows(IllegalArgumentException.class, () ->
          replayServiceImp.createNewReplay(requests));
    assertEquals("Start and end can not be the same time", e.getMessage());
    Mockito.doCallRealMethod().when(proxyHelper).sseHelper(null, requests, new SseEmitter());
    requests.setEnd(LocalDateTime.now());
    var id = replayServiceImp.createNewReplay(requests);

    assertNotNull(replayServiceImp.getReplay(id));
  }

  @Test
  void destroyDataStream() {
    Requests requests = new Requests();
    requests.setStart(LocalDateTime.now().minusMinutes(1));
    requests.setEnd(requests.getStart());
    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () ->
        replayServiceImp.createNewReplay(requests));

    if(!e.getMessage().contains("Connection to the database failed"))
      assertEquals("Neither information, nor start, nor end, nor sensors can be empty",
          e.getMessage());
    requests.setSensors(List.of("test"));
    if(!e.getMessage().contains("Connection to the database failed"))
      e = Assertions.assertThrows(IllegalArgumentException.class, () ->
          replayServiceImp.createNewReplay(requests));
    assertEquals("Start and end can not be the same time", e.getMessage());
    Mockito.doCallRealMethod().when(proxyHelper).sseHelper(null, requests, new SseEmitter());
    requests.setEnd(LocalDateTime.now());
    var id = replayServiceImp.createNewReplay(requests);

    replayServiceImp.destroyReplay(id);
    assertNull(replayServiceImp.getReplay(id));

  }
  @Configuration
  @Import(ReplayServiceImp.class)
  static class TestConfig {
    @Bean
    DatastreamService datastreamService() {
      return mock(DatastreamService.class);
    }
    @Bean
    ObservationRepository observationRepository() {
      return mock(ObservationRepository.class);
    }
    @Bean
    SensorServiceImp sensorServiceImp() {
      return mock(SensorServiceImp.class);
    }
    @Bean
    ProxyHelper proxyHelper() {
      return mock(ProxyHelper.class);
    }
  }
}
