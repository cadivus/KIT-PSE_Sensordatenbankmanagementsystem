package edu.teco.sensordatenbankmanagementsystem.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservationStats;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class ThingServiceImpTest {

  @SpyBean
  ThingServiceImp thingServiceImp;
  @MockBean
  ThingRepository thingRepository;
  @MockBean
  DatastreamRepository datastreamRepository;
  @MockBean
  ObservationService observationService;

  @Test
  public void getListOfClosestSensors() {
    assert new ArrayList<>().equals(thingServiceImp.getListOfClosestSensors(0, 0, 0));
  }


  @Test
  void getThing() {
    Mockito.when(thingRepository.findById("saqn:t:grimm-aerosol.com:edm80opc:sn19001")).thenReturn(
        java.util.Optional.of(new Thing()));
    assertEquals(thingServiceImp.getThing("saqn:t:grimm-aerosol.com:edm80opc:sn19001"),
        new Thing());
    NoSuchElementException e = Assertions
        .assertThrows(NoSuchElementException.class, () -> thingServiceImp.getThing("xyz"));
  }

  @Test
  void getWhetherThingsActive() {
    Mockito.when(
        datastreamRepository.findDatastreamsByThing_Id("saqn:t:grimm-aerosol.com:edm80opc:sn19001"))
        .thenReturn(new ArrayList<Datastream>());
    assertEquals(thingServiceImp
            .getWhetherThingsActive(List.of("saqn:t:grimm-aerosol.com:edm80opc:sn19001"), 10),
        List.of(-1));
  }

  @Test
  @Disabled("Missing NPE catch")
  void getActiveRateOfThings() {
    LocalDateTime frameStart = LocalDate.of(2020, 1, 1).atStartOfDay();
    LocalDateTime frameEnd = LocalDateTime.now();
    Mockito.when(
        observationService.getObservationsByThingId("saqn:t:grimm-aerosol.com:edm80opc:sn19001",
            Integer.MAX_VALUE,
            Sort.unsorted(), (List<String>) null, frameStart, frameEnd))
        .thenReturn(new ArrayList<Observation>());

    assertEquals(thingServiceImp
        .getActiveRateOfThings(List.of("saqn:t:grimm-aerosol.com:edm80opc:sn19001"), frameStart,
            frameEnd), List.of(0.0));
    assertEquals(thingServiceImp.getActiveRateOfThings(List.of("xyz"), frameStart, frameEnd),
        List.of(0.0));
    assertEquals(thingServiceImp.getActiveRateOfThings(List.of("xyz"), null, frameEnd),
        List.of(0.0));
    assertEquals(thingServiceImp.getActiveRateOfThings(List.of("xyz"), null, null),
        List.of(0.0));
  }

  @Test
  void getObservationStatsOfThings() {
    LocalDateTime frameStart = LocalDate.of(2020, 1, 1).atStartOfDay();
    LocalDateTime frameEnd = LocalDateTime.now();
    Mockito.when(
        observationService.getObservationsByThingId("saqn:t:grimm-aerosol.com:edm80opc:sn19001",
            Integer.MAX_VALUE,
            Sort.unsorted(), (List<String>) null, frameStart, frameEnd))
        .thenReturn(new ArrayList<Observation>());

    assertEquals(thingServiceImp
        .getObservationStatsOfThings(List.of("saqn:t:grimm-aerosol.com:edm80opc:sn19001"),
            List.of("saqn:o:PM25"), frameStart, frameEnd).getClass(), ArrayList.class);
  }

  @Test
  void getAllThings() {
    Mockito.when(thingRepository.findAll()).thenReturn(new ArrayList<Thing>());
    assertEquals(thingServiceImp.getAllThings(), new ArrayList<Thing>());
  }

  @Configuration
  @Import(ReplayServiceImp.class)
  static class TestConfig {

    @Bean
    ThingRepository thingRepository() {
      return mock(ThingRepository.class);
    }

    @Bean
    DatastreamRepository datastreamRepository() {
      return mock(DatastreamRepository.class);
    }

    @Bean
    SensorServiceImp sensorServiceImp() {
      return mock(SensorServiceImp.class);
    }

    @Bean
    ObservationServiceImp observationServiceImp() {
      return mock(ObservationServiceImp.class);
    }

    @Bean
    ProxyHelper proxyHelper() {
      return mock(ProxyHelper.class);
    }
  }
}
