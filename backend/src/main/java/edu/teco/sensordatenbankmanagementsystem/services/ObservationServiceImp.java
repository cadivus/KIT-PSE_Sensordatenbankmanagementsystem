package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The ObservationServiceImp is an implementation of the {@link ObservationService} interface
 * catered towards using the TECO database
 */
@Service
@CommonsLog(topic = "Observationservice")
public class ObservationServiceImp implements ObservationService {

  ObservationRepository repository;
  DatastreamRepository datastreamRepository;

  Map<UUID, SseEmitter> sseStreams = new HashMap<UUID, SseEmitter>();

  @Autowired
  public ObservationServiceImp(ObservationRepository repository,
      DatastreamRepository datastreamRepository) {
    this.repository = repository;
    this.datastreamRepository = datastreamRepository;
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

  /**
   * {@inheritDoc}
   *
   * @return
   */
  @Transactional
  @Cacheable("Datastreams")
  public List<Observation> getObservationByDatastream(Datastream datastream, LocalDateTime start,
      LocalDateTime end) {
    List<Observation> result;
      if (start == null) {
          result = repository.findObservationsByDatastream(datastream.getId())
              .collect(Collectors.toList());
      } else if (end == null) {
          result = repository
              .findObservationsByDatastreamAndPhenomenonStartAfter(datastream.getId(), start)
              .collect(Collectors.toList());
      } else {
          result = repository
              .findObservationsByDatastreamAndPhenomenonStartAfterAndPhenomenonEndBefore(
                  datastream.getId(), start, end).collect(Collectors.toList());
      }
    return result;
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
