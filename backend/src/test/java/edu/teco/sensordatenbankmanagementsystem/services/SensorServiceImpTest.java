package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.repository.SensorRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
class SensorServiceImpTest {

  @MockBean
  SensorRepository sensorRepository;
  @MockBean
  DatastreamService datastreamService;
  @Autowired
  SensorServiceImp sensorServiceImp;


  @Test
  void getSensor() {
    Mockito.when(sensorRepository.findById("test")).thenReturn(java.util.Optional.of(new Sensor()));
    assertEquals(sensorServiceImp.getSensor("test"), new Sensor());
  }

  @Test
  void getAllSensors() {
    Mockito.when(sensorRepository.findAll()).thenReturn(List.of(new Sensor()));
    assertEquals(sensorServiceImp.getAllSensors(), List.of(new Sensor()));
  }

  @Configuration
  @Import(SensorServiceImp.class)
  static class TestConfig {

    @Bean
    SensorRepository sensorRepository() {
      return mock(SensorRepository.class);
    }
    @Bean
    DatastreamService datastreamService() {
      return mock(DatastreamService.class);
    }
  }
}
