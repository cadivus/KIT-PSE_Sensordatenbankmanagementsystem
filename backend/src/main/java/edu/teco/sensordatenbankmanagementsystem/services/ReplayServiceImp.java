package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.util.ProxyHelper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@CommonsLog
public class ReplayServiceImp implements ReplayService {

  private final SensorService sensorService;
  private final ProxyHelper proxyHelper;

  BidiMap<UUID, SseEmitter> sseStreams = new DualHashBidiMap<>();


  public ReplayServiceImp(SensorService sensorService,
      ProxyHelper proxyHelper) {
    this.sensorService = sensorService;
    this.proxyHelper = proxyHelper;
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

}
