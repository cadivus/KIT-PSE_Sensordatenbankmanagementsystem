package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Location;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.ObservationStats;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public interface ThingService {

    @Transactional
    public Thing getThing(String id);

    /**
     * Gets whether the given things were active in the last X days
     * @param thingIds of things to check activity of
     * @param days to classify recent activity by
     * @return active status of the given things in order
     */
    public List<Integer> getWhetherThingsActive(List<String> thingIds, int days);

    /**
     * Gets active rate of things, calculated as amount of data transmissions / days
     * @param thingsIds of things to get active rate of
     * @param frameStart start of time frame to calculate active rate from
     * @param frameEnd end of time frame to calculate active rate from
     * @return active rate of the given things in order
     */
    public List<Double> getActiveRateOfThings(List<String> thingsIds, LocalDateTime frameStart, LocalDateTime frameEnd);

    public List<ObservationStats> getObservationStatsOfThings(List<String> thingsIds, List<String> obsIds,
                                                              LocalDateTime frameStart, LocalDateTime frameEnd);

    /**
     * This gets the list of all sensors (or things) from the database and orders them by distance
     * to a specified point
     *
     * @param lat Latitude of the point
     * @param lon Longitude of the point
     * @param el  elevation of the point
     * @return The ordered List
     */
    @Cacheable("Sensors")
    public List<Thing> getListOfClosestSensors(double lat, double lon, double el);


    /**
     * Gets all tings, skr
     * @return all things in the entire universe
     */
    public List<Thing> getAllThings();

    List<List<String>> getThingsObsIds(List<String> thingIds);
}
