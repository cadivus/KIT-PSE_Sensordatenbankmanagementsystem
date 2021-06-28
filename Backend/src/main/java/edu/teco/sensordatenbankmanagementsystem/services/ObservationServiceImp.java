package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.controllers.ObservationController;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ObservationServiceImp is an implementation of the {@link ObservationService} interface catered towards using the TECO database
 */
@Service
@CommonsLog
public class ObservationServiceImp implements ObservationService {

    ObservationRepository repository;
    Map<UUID, SseEmitter> sseStreams = new HashMap<UUID, SseEmitter>();

    @Autowired
    public ObservationServiceImp(ObservationRepository repository){
        this.repository = repository;
    }


    /**
     * {@inheritDoc}
     */
    public UUID createNewDataStream(JSON information) {
        SseEmitter emitter = new SseEmitter();
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
        return id;
    }

    /**
     * {@inheritDoc}
     */
    public UUID createReplay(JSON information){
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
    public void destroyDataStream(UUID id){
        sseStreams.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    public Observation getObservation(Long id) {
        return repository.findById(id).get();
    }
}
