package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

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
  void getDataStream() {
  }

  @Test
  void destroyDataStream() {
  }

  @Test
  void getObservationsBySensorId() {
  }

  @Test
  @Disabled("Until we can figure out how to create a database connection on the github")
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

}
