package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;

import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The ObservationServiceImp is an implementation of the {@link ObservationService} interface
 * catered towards using the TECO database
 */
@Service
@CommonsLog(topic = "Observationservice")
@Transactional
public class ObservationServiceImp implements ObservationService{

  final ObservationRepository observationRepository;
  @Autowired
  DatastreamRepository datastreamRepository;
  @Autowired
  SensorService sensorService;
  final ProxyHelper proxyHelper;
  final ObservedPropertyRepository observedPropertyRepository;

  BidiMap<UUID, SseEmitter> sseStreams = new DualHashBidiMap<>();

  @Autowired
  public ObservationServiceImp(ObservationRepository observationRepository,
      ProxyHelper proxyHelper, ObservedPropertyRepository observedPropertyRepository) {
    this.observationRepository = observationRepository;
    this.proxyHelper = proxyHelper;
    this.observedPropertyRepository = observedPropertyRepository;
  }

  /**
   * {@inheritDoc}
   */
  @Transactional()
  public UUID createNewDataStream(Requests information) {
    if(information == null || information.getStart() == null || information.getEnd() == null || information.getSensors() == null || information.getSensors().isEmpty()){
      throw new IllegalArgumentException("Neither information, nor start, nor end, nor sensors can be empty");
    }
    if (information.getSpeed() == 0) {
      information.setSpeed(1);
    }
    if (information.getSpeed() > 1 && information.getEnd().isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Speed can not be over 1 with the end in the future");
    }
    if (information.getStart().equals(information.getEnd()))
      throw new IllegalArgumentException("Start and end can not be the same time");
    Long life = (long) (ChronoUnit.MILLIS.between(information.getStart(), information.getEnd())/information.getSpeed() * 1.05);
    SseEmitter emitter = new SseEmitter(life);
    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();

    UUID id = UUID.randomUUID();
    sseStreams.put(id, emitter);
    List<Datastream> datastreams = sensorService
        .getDatastreams(information.getSensors(), information.getStart(),
            information.getEnd()).collect(Collectors.toList());
    if (datastreams.size() == 0)
        datastreams.add(new Datastream());
    sseMvcExecutor.execute(() -> {
      proxyHelper.sseHelper(datastreams, information, emitter);

    });

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
    information.setSpeed(1);
    return createNewDataStream((information));
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
    LocalDateTime finalFrameStart = frameStart;
    LocalDateTime finalFrameEnd = frameEnd;

    //this alternative would utilize a native query, but wouldn't be able to integrate the sort in the query
    //String orderBySQLString = sort.stream().map(Sort.Order::getProperty).collect(Collectors.joining(","));
    //return this.observationRepository.findObservationsInDatastreams(associatedStreams, "phenomenonStart", PageRequest.of(0, limit)).collect(Collectors.toList());

    List<Datastream> associatedStreams = Optional
            .ofNullable(filter).map(s -> this.datastreamRepository.findDatastreamsByThing_IdAndObsIdIn(thingId, s))
            .orElseGet(() -> this.datastreamRepository.findDatastreamsByThing_Id(thingId));

//    int filterCount = filter == null ? -1 : filter.size();
//    System.out.printf("%s %s %s %s %s %s\n", thingId, limit, sort, filter, frameStart, frameEnd);
//    System.out.printf("found %s datastreams of %s\n", associatedStreams.size(), filterCount);
//    for(Datastream d : associatedStreams){
//      System.out.printf("ids: %s %s\n", d.getThing().getId(), d.getObsId());
//      System.out.printf(
//              "%s items in %s\n",
//              observationRepository.countAllByDatastreamId(d.getId()),
//              d.getId()
//      );
//      System.out.printf("%s filtered items in %s\n",
//              observationRepository.findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(d.getId(), finalFrameStart, finalFrameEnd, null).count(),
//              d.getId());
//    }
//    System.out.printf("limit is %s\n", limit);

    return associatedStreams.stream()
            .flatMap(a->this.observationRepository
                    .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBefore(a.getId(),
                            finalFrameStart, finalFrameEnd, PageRequest.of(0, limit).withSort(sort))
            )
            .limit(limit)
            .collect(Collectors.toList());
  }

  @Override
  public List<ObservedProperty> getAllObservedProperties() {
    return observedPropertyRepository.findAll();
  }

  @Override
  @Transactional
  public Stream<Observation> getObservationByDatastreamId(String datastreamId, Pageable page){
    return observationRepository.findObservationsByDatastreamId(datastreamId, page);
  }

  /**
   * {@inheritDoc}
   *
   * @return
   */

  @Transactional
  @Cacheable("Observations")
  public Stream<Observation> getObservationByDatastream(Stream<Datastream> datastreams,
      LocalDateTime start, LocalDateTime end)  {
    if (datastreams == null) {
      return null;
    }
    return datastreams.flatMap(d -> {
      if (start == null) {
        return observationRepository.findObservationsByDatastreamId(d.getId());
      } else if (end == null) {
        return observationRepository
            .findObservationsByDatastreamIdAndPhenomenonStartAfter(d.getId(), start);
      } else {
        return observationRepository
            .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(
                d.getId(), start, end);
      }
    });
  }

}
