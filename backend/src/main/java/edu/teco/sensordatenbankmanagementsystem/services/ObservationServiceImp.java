package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The ObservationServiceImp is an implementation of the {@link ObservationService} interface
 * catered towards using the TECO database
 */
@Service
@CommonsLog(topic = "Observationservice")
public class ObservationServiceImp implements ObservationService {

  ObservationRepository observationRepository;
  DatastreamRepository datastreamRepository;
  ObservedPropertyRepository observedPropertyRepository;

  Map<UUID, SseEmitter> sseStreams = new HashMap<UUID, SseEmitter>();

  @Autowired
  public ObservationServiceImp(
          ObservationRepository observationRepository,
          DatastreamRepository datastreamRepository,
          ObservedPropertyRepository observedPropertyRepository
  ) {
    this.observationRepository = observationRepository;
    this.datastreamRepository = datastreamRepository;
    this.observedPropertyRepository = observedPropertyRepository;
  }

  /**
   * {@inheritDoc}
   */
  public UUID createNewDataStream(Requests information) {
    SseEmitter emitter = new SseEmitter(86400000L);
    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
    sseMvcExecutor.execute(() -> {
      try {
        for (int i = 0; true; i++) {
          SseEmitter.SseEventBuilder event = SseEmitter.event()
              .data("SSE MVC - " + LocalTime.now().toString())
              .id(String.valueOf(i))
              .name("sse event - mvc");
          emitter.send(event);
          Thread.sleep(1000);
        }
      } catch (Exception ex) {
        emitter.completeWithError(ex);
      }
    });
    UUID id = UUID.randomUUID();
    sseStreams.put(id, emitter);
    log.info("finished datastream creation for id: " + id);
    return id;
  }

  @Override
  public Observation getObservation(String id) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public UUID createReplay(Requests information) {
    return UUID.randomUUID();
  }

  /**
   * {@inheritDoc}
   */
  public SseEmitter getDataStream(UUID id) {
    return sseStreams.get(id);
  }

  /**
   * {@inheritDoc}
   */
  public void destroyDataStream(UUID id) {
    sseStreams.remove(id);
  }

  @Transactional
  @Override
  public List<Observation> getObservationsByThingId(
          String thingId,
          int limit,
          Sort sort,
          List<String> filter,
          LocalDateTime frameStart, LocalDateTime frameEnd) {
    if(frameStart == null){
      frameStart = LocalDate.of(0, 1, 1).atStartOfDay();
    }
    if(frameEnd == null){
      frameEnd = LocalDateTime.now();
    }

    //this alternative would utilize a native query, but wouldn't be able to integrate the sort in the query
    //String orderBySQLString = sort.stream().map(Sort.Order::getProperty).collect(Collectors.joining(","));
    //return this.observationRepository.findObservationsInDatastreams(associatedStreams, "phenomenonStart", PageRequest.of(0, limit)).collect(Collectors.toList());

    List<Datastream> associatedStreams = Optional
            .ofNullable(filter).map(s -> this.datastreamRepository.findDatastreamsByThingIdAndObsIdIn(thingId, s))
            .orElseGet(() -> this.datastreamRepository.findDatastreamsByThingId(thingId));

//    System.out.printf("%s %s %s %s %s %s\n", thingId, limit, sort, filter, frameStart, frameEnd);
//    System.out.println(associatedStreams.size());
//    for(Datastream d : associatedStreams){
//      System.out.printf("ids: %s %s\n", d.getThingId(), d.getObsId());
//      System.out.printf(
//              "%s items in %s\n",
//              observationRepository.countAllByDatastreamId(d.getId()),
//              d.getId()
//      );
//    }

    LocalDateTime finalFrameStart = frameStart;
    LocalDateTime finalFrameEnd = frameEnd;
    return associatedStreams.stream()
            .flatMap(a-> this.observationRepository
                    .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBefore(a.getId(),
                            finalFrameStart, finalFrameEnd, PageRequest.of(0, limit).withSort(sort)))
            .limit(limit)
            .collect(Collectors.toList());
  }

  @Override
  public List<ObservedProperty> getAllObservedProperties() {
    return observedPropertyRepository.findAll();
  }

  /**
   * {@inheritDoc}
   *
   * @return
   */
  @Transactional
  @Cacheable("Datastreams")
  public List<Observation> getObservationsByDatastream(Datastream datastream, LocalDateTime start,
                                                       LocalDateTime end) {
    List<Observation> result;
    if (start == null) {
      result = observationRepository.findObservationsByDatastreamId(datastream.getId(), null)
          .collect(Collectors.toList());
    } else if (end == null) {
      result = observationRepository
          .findObservationsByDatastreamIdAndPhenomenonStartAfter(datastream.getId(), start)
          .collect(Collectors.toList());
    } else {
      result = observationRepository
          .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBefore(
              datastream.getId(), start, end, (Pageable)null).collect(Collectors.toList());
    }
    return result;
  }

}
