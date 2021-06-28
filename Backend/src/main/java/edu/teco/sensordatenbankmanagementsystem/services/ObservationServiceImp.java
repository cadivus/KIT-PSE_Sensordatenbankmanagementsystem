package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

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
    public UUID createReplay(Requests information){
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

    public void dataAggregation() {

    }
    private Stream<Observation> fillGaps(Stream<Observation> observations) {

        final Var prev = new Var(); // required to be final, so a wrapper is needed to modify the instance

        Stream<Observation> result = observations
                .map(curr -> {
                    final ArrayList<Observation> sub = new ArrayList<>();

                    if(prev.instance != null) {
                        for (LocalDate date = prev.instance.date.plusDays(1); date.isBefore(curr.date); date = date.plusDays(1)) {
                            sub.add(new Observation(date, prev.instance.value));
                        }
                    }

                    sub.add(curr);
                    prev.instance = curr;

                    return sub;
                })
                .flatMap( l -> l.stream());

        return result;
    }

    // Helper class
    class Var {
        public Observation instance;
    }
}
