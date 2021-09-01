package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class ObservationServiceImpTest {

  @Autowired
  ObservationServiceImp observationServiceImp;


  @MockBean
  DatastreamService datastreamService;
  @MockBean
  ObservationRepository observationRepository;
  @MockBean
  DatastreamRepository datastreamRepository;
  @MockBean
  ObservedPropertyRepository observedPropertyRepository;
  @SpyBean
  ProxyHelper proxyHelper;

  @Test
  void getObservationsBySensorId() {
    String thingId = "saqn:t:grimm-aerosol.com:edm80opc:sn19001";
    int limit = 10000;
    Sort sort = Sort.by("resultNumber");
    List<String> filter = List.of("");
    LocalDateTime frameStart = LocalDate.of(2018,1,1).atStartOfDay();
    LocalDateTime frameEnd = LocalDateTime.now();
    Mockito.when(datastreamRepository.findDatastreamsByThing_Id(thingId)).thenReturn(
        new ArrayList<Datastream>());
    Mockito.when(datastreamRepository.findDatastreamsByThing_IdAndObsIdIn(thingId, filter)).thenReturn(new ArrayList<Datastream>());
    assertEquals(new ArrayList<Observation>(),observationServiceImp.getObservationsByThingId(thingId, limit, sort.ascending(), filter, frameStart, frameEnd));
    assertEquals(new ArrayList<Observation>(),observationServiceImp.getObservationsByThingId(thingId, limit, sort.ascending(), filter, null, frameEnd));
    assertEquals(new ArrayList<Observation>(),observationServiceImp.getObservationsByThingId(thingId, limit, sort.ascending(), filter, frameStart, null));
    assertEquals(new ArrayList<Observation>(),observationServiceImp.getObservationsByThingId(thingId, limit, null, filter, frameStart, frameEnd));
    assertEquals(new ArrayList<Observation>(),observationServiceImp.getObservationsByThingId(thingId, 0, sort.ascending(), filter, frameStart, frameEnd));
    assertEquals(new ArrayList<Observation>(),observationServiceImp.getObservationsByThingId(null, limit, sort.ascending(), filter, frameStart, frameEnd));

  }

  @Test
  void getObservationByDatastream() {
    Datastream datastream = new Datastream();
    LocalDateTime start = null;
    LocalDateTime end = null;
    datastream.setId("test");
    Stream<Datastream> datastreams1 = Stream.of(new Datastream());
    Stream<Datastream> datastreams2 = Stream.of(new Datastream());
    Stream<Datastream> datastreams3 = Stream.of(new Datastream());
    Mockito.when(observationRepository.findObservationsByDatastreamId("test"))
        .thenReturn(Stream.of(new Observation()));
    try {
      Stream<Observation> streamNullNull = observationServiceImp
          .getObservationByDatastream(datastreams1, start, end);
      Stream<?> emptyStream1 = Stream.empty();
      checkIfTwoStreamsAreTheSame(streamNullNull, emptyStream1);
      start = LocalDateTime.now();
      Mockito.when(
          observationRepository
              .findObservationsByDatastreamIdAndPhenomenonStartAfter("test", start))
          .thenReturn(Stream.of(new Observation()));
      Stream<?> emptyStream2 = Stream.empty();
      Stream<?> streamDateNull = observationServiceImp
          .getObservationByDatastream(datastreams2, start, end);
      checkIfTwoStreamsAreTheSame(streamDateNull, emptyStream2);

      end = LocalDateTime.now();
      Mockito.when(observationRepository
          .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartAsc(
              "test", start, end)).thenReturn(Stream.of(new Observation()));
      Stream<?> emptyStream3 = Stream.empty();
      Stream<?> streamDateDate = observationServiceImp
          .getObservationByDatastream(datastreams3, start, end);
      checkIfTwoStreamsAreTheSame(streamDateDate, emptyStream3);
    } catch (CannotCreateTransactionException ex) {

    }
  }

  private void checkIfTwoStreamsAreTheSame(Stream<?> s1, Stream<?> s2){
    Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
    while (iter1.hasNext() && iter2.hasNext()) {
      assertEquals(iter1.next(), iter2.next());
    }
    assert !iter1.hasNext() && !iter2.hasNext();

  }

  @Configuration
  @Import(ObservationServiceImp.class)
  static class TestConfig {
    @Autowired
    EntityManager entityManager;

    @Bean
    ObservationRepository observationRepository() {
      return mock(ObservationRepository.class);
    }

    @Bean
    ObservedPropertyRepository observedPropertyRepository() {
      return mock(ObservedPropertyRepository.class);
    }
    @Bean
    DatastreamService datastreamService() {
      return mock(DatastreamService.class);
    }
    @Bean
    DatastreamRepository datastreamRepository() {
      return mock(DatastreamRepository.class);
    }
    @Bean
    SensorService sensorService(){
      return mock(SensorService.class);
    }

  }

}
