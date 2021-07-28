package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import edu.teco.sensordatenbankmanagementsystem.models.Requests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The ObservationService provides more complex methods for functionality concerning the querying of {@link Observation} data based on the repositories.
 */
public interface ObservationService {
    /**
     * This method creates a new SSEEmitter DataStream using the information provided in the parameter as well as a
     * repository
     * @param information This should contain the specific information about the Datastream that is to be created.
     *                    At least sensor, interval and start date need to be in here
     * @return The UUID of the newly created Datastream
     */
    UUID createNewDataStream(Requests information);

    /**
     * This returns a single Observation Model from the Repository
     * @param id The ID of the Observation
     * @return
     */
    Observation getObservation(String id);
  
    List<Observation> getObservationsByDatastream(Datastream id, LocalDateTime start, LocalDateTime end);

    /**
     * This will create a replay of one or more Sensors. It will work akin to the {@link #createNewDataStream(Requests)} but with
     * live data opposed to using already existing data
     * @param information This should contain the Sensor Information for the replay
     * @return The UUID under which the Replay is to be reached
     */
    UUID createReplay(Requests information);

    /**
     * This will return a previously created Datastream from its specified UUID.
     * If there is no DataStream under the given UUID, none will be created
     * @param id The UUID of the Datastream
     * @return An {@link org.springframework.web.servlet.mvc.method.annotation.SseEmitter}
     */
    SseEmitter getDataStream(UUID id);

    /**
     * This will delete the Datastream from the database and will make it send its closing message
     * @param id The UUID of the Datastream
     */
    void destroyDataStream(UUID id);

    List<Observation> getObservationsByThingId(String thingId, int limit, Sort sort, List<String> filter, LocalDateTime frameStart, LocalDateTime frameEnd);

    List<ObservedProperty> getAllObservedProperties();

    /**
     * interpolates expected numerical observation values on a given set of observation data
     */
    class Interpolator {

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
}

