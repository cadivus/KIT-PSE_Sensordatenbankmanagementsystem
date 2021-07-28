package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
public class ObservationServiceImp implements ObservationService {

  ObservationRepository observationRepository;
  DatastreamRepository datastreamRepository;
  SensorService sensorService;
  ProxyHelper proxyHelper;

  BidiMap<UUID, SseEmitter> sseStreams = new DualHashBidiMap<>();

  @Autowired
  public ObservationServiceImp(ObservationRepository observationRepository,
      DatastreamRepository datastreamRepository, SensorService sensorService,
      ProxyHelper proxyHelper) {
    this.observationRepository = observationRepository;
    this.datastreamRepository = datastreamRepository;
    this.sensorService = sensorService;
    this.proxyHelper = proxyHelper;
  }

  /**
   * {@inheritDoc}
   */
  @Transactional()
  public UUID createNewDataStream(Requests information) {
    if (information.getSpeed() == 0) {
      information.setSpeed(1);
    }
    if(information == null || information.getStart() == null || information.getEnd() == null || information.getSensors() == null || information.getSensors().isEmpty()){
      throw new IllegalArgumentException("Neither information, nor start, nor end, nor sensors can be empty");
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
  @Cacheable("Observations")
  public List<Observation> getObservationsBySensorId(String sensorId, int limit, Sort sort,
      String filter) {
    String orderBySQLString = sort.stream().map(Sort.Order::getProperty)
        .collect(Collectors.joining(","));

    List<Datastream> associatedStreams = Optional.ofNullable(filter)
        .map(s -> this.datastreamRepository.findDatastreamsBySensorIdAndObsId(sensorId, s))
        .orElseGet(() -> this.datastreamRepository.findDatastreamsBySensorId(sensorId));

    //System.out.println(associatedStreams.stream().map(Datastream::getId).collect(Collectors.toList()));
    //the following line would utilize a native query, but wouldn't be able to integrate the sort in the query
    //return this.observationRepository.findObservationsInDatastreams(associatedStreams, "phenomenonStart", PageRequest.of(0, limit)).collect(Collectors.toList());
    return associatedStreams.stream()
        .flatMap(a -> this.observationRepository
            .findObservationsByDatastreamId(a.getId(), PageRequest.of(0, limit).withSort(sort)))
        .limit(limit)
        .collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   *
   * @return
   */
  @Transactional
  @Cacheable("Datastreams")
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


  /**
   * interpolates expected numerical observation values on a given set of observation data
   */
  public static class Interpolator {

    private final List<Double> f;
    private final List<Double> x;

    /**
     * interpolates by calculating the newton representation of the interpolation polynomial as we
     * don't have all function values at arbitrary positions, thus can not choose more efficient
     * interpolation points
     *
     * @param interpolationPoints points to interpolate to, don't overdo the amount
     */
    public Interpolator(Collection<Observation> interpolationPoints) {
      List<Observation> points = new ArrayList<>(interpolationPoints);
      int N = points.size();
      this.x = points.stream().map(e -> (double) e.getDate().toEpochDay())
          .collect(Collectors.toList());
      this.f = new ArrayList<>(N);
      List<Double> tmp = points.stream().map(Observation::getValue).collect(Collectors.toList());
      for (int i = N; i > 0; i--) {
        if (i < N) {
          for (int j = 0; j < i; j++) {
            tmp.set(j, (tmp.get(j) - tmp.get(j + 1)) / (this.x.get(j) - this.x.get(j + N - i)));
          }
        }
        this.f.add(tmp.get(i - 1));
      }
    }

    /**
     * gets the interpolated value at the given date
     *
     * @param date to get the value at
     * @return interpolated value at the given date
     */
    public Observation getAt(LocalDate date) {
      double at = date.toEpochDay();
      double value = this.f.get(0);
      double poly = 1;
      for (int i = 1; i < this.f.size(); i++) {
        poly *= (at - this.x.get(i - 1));
        value += this.f.get(i) * poly;
      }
      return new Observation(value, date);
    }
  }

//    private Stream<Observation> fillGaps(Stream<Observation> observations) {
//
//        Var prev = new Var(); //trying out whether something fails without final,  required to be final, so a wrapper is needed to modify the instance
//
//        Stream<Observation> result = observations
//                .map(curr -> {
//                    final ArrayList<Observation> sub = new ArrayList<>();
//
//                    if(prev.instance != null) {
//                        for (LocalDate date = prev.instance.date.plusDays(1); date.isBefore(curr.date); date = date.plusDays(1)) {
//                            sub.add(new Observation(date, prev.instance.value));
//                        }
//                    }
//
//                    sub.add(curr);
//                    prev.instance = curr;
//
//                    return sub;
//                })
//                .flatMap( l -> l.stream());
//
//        return result;
//    }
//
//    // Helper class
//    class Var {
//        public Observation instance;
//    }
}
