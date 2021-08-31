package edu.teco.sensordatenbankmanagementsystem.util;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
/**
 * Due to the Spring framework reliance on proxies to use most of its annotations any method
 * that is annotated with @Transactional can not be called from the same class in which it lies.
 * Additionally the @Transactional annotation is thread bound so that any method that would create
 * a new thread for part of its execution would lose the transactional status halfway through
 * execution. This class can hold methods that would fall into one of these categories
 */
public class ProxyHelper {

  final ObservationRepository observationRepository;
  final EntityManager em;
  final SensorService sensorService;

  public ProxyHelper(ObservationRepository observationRepository, EntityManager em,
      SensorService sensorService) {
    this.observationRepository = observationRepository;
    this.em = em;
    this.sensorService = sensorService;
  }

  /**
   * The sseHelper should be part of the ObservationService but due to the pitfalls mentioned in
   * this classes Javadoc, mainly the new thread creation it needs to be in a different method
   * and because of the proxy problem it has to be in a different class as well
   * This will take a stream of datastreams and try to find all Observations between the start
   * and end date which are in these datastreams and will output them into the specified SseEmitter
   * @param datastreams A datastream is part of the Frost Database by Teco. This receives a stream
   *                    of those
   * @param information This should contain the specific information about the Datastream that is to
   *                    be created.
   *                    At least sensor(s), speed and start date need to be in here
   * @param emitter This is an instance of a SSEEmitter, which implements the Server sent events
   *                html protocol
   */
  @Transactional
  public void sseHelper(List<Datastream> datastreams, Requests information,
      SseEmitter emitter) {
    try {
      while (information.getStart().isBefore(information.getEnd())) {
        for (Datastream d : datastreams) {
          /*
          if (d.getPhenomenonEnd().isBefore(information.getStart()) && d.getPhenomenonStart().isBefore(information.getStart())){
            datastreams.remove(d);
          } */
          try (Stream<Observation> observations = observationRepository
              .findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(
                  d.getId(), information.getStart(),
                  information.getEnd())) {
            observations.filter(Objects::nonNull).reduce((current, next) -> {
                  SseEmitter.SseEventBuilder event = SseEmitter.event()
                      .data(current.toString() + d.getUnit() + d.getSensorId());
                  try {
                    emitter.send(event);

                  } catch (IOException ex) {
                    emitter.completeWithError(ex);
                  }
                  try {
                    Thread.sleep(ChronoUnit.MILLIS
                        .between(current.getPhenomenonStart(),
                            next.getPhenomenonStart())
                        / information.getSpeed());

                  } catch (InterruptedException ex) {
                    emitter.completeWithError(ex);
                  }
                  em.detach(current);
                  information.setStart(current.getPhenomenonEnd());
                  return next;
                }
            );
          }
        }
      Thread.sleep(10000);
        datastreams = sensorService
            .getDatastreams(information.getSensors(), information.getStart(),
                information.getEnd()).collect(Collectors.toList());
      }
      emitter.complete();
    } catch (Exception ex) {
      try {
        emitter.completeWithError(ex);
      } catch (IllegalStateException ignored){}
    }
  }
}
