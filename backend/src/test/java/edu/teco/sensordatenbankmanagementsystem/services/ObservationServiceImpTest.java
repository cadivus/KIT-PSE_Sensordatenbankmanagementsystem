package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDateTime;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SpringBootTest
public class ObservationServiceImpTest {

  @Autowired
  ObservationServiceImp observationServiceImp;

  @MockBean
  ObservationRepository observationRepository;
  @MockBean
  DatastreamRepository datastreamRepository;
  @SpyBean
  ProxyHelper proxyHelper;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void createNewDataStream() {
    Requests requests = new Requests();
    requests.setStart(LocalDateTime.now());
    requests.setEnd(LocalDateTime.now());
    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () ->
        observationServiceImp.createNewDataStream(requests));
    assertEquals("Neither information, nor start, nor end, nor sensors can be empty", e.getMessage());
    requests.setSensors(List.of("test"));
    Mockito.doCallRealMethod().when(proxyHelper).sseHelper(null, requests,new SseEmitter());
    e = Assertions.assertThrows(IllegalArgumentException.class, () ->
        observationServiceImp.createNewDataStream(requests));
    assertEquals("Start and end can not be the same time", e.getMessage());

  }

  @Test
  void getObservation() {
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

  @Test
  void getObservationsBySensorId() {
  }

  @Test
  void getObservationByDatastream() {
  }
}