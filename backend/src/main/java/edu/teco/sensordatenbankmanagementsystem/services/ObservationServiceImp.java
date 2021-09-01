package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;

import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The ObservationServiceImp is an implementation of the {@link ObservationService} interface
 * catered towards using the TECO database
 */
@Service
@CommonsLog(topic = "Observationservice")
@Transactional
public class ObservationServiceImp implements ObservationService{

  final ObservationRepository observationRepository;
  // @Autowired
  final DatastreamRepository datastreamRepository;
  final ObservedPropertyRepository observedPropertyRepository;


  @Autowired
  public ObservationServiceImp(ObservationRepository observationRepository,
      DatastreamRepository datastreamRepository,
      ObservedPropertyRepository observedPropertyRepository) {
    this.observationRepository = observationRepository;
    this.datastreamRepository = datastreamRepository;
    this.observedPropertyRepository = observedPropertyRepository;
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
            .ofNullable(filter).map(s -> this.datastreamRepository.findDatastreamsByThing_IdAndObsIdIn(thingId, s))
            .orElseGet(() -> this.datastreamRepository.findDatastreamsByThing_Id(thingId));

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
                    .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(a.getId(),
                            finalFrameStart, finalFrameEnd, PageRequest.of(0, limit).withSort(sort)))
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
            .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartAsc(
                d.getId(), start, end);
      }
    });
  }

}
