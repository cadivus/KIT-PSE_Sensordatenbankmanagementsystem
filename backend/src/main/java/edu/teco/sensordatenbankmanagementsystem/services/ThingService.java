package edu.teco.sensordatenbankmanagementsystem.services;

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
public class ThingService {

    private final ThingRepository thingRepository;
    private final DatastreamRepository datastreamRepository;
    private final ObservationService observationService;

    @Autowired
    public ThingService(
            ThingRepository thingRepository,
            DatastreamRepository datastreamRepository,
            ObservationService observationService) {
        this.thingRepository = thingRepository;
        this.datastreamRepository = datastreamRepository;
        this.observationService = observationService;
    }

    @Transactional
    public Thing getThing(String id) {
        return thingRepository.findById(id).get();
    }

    /**
     * Gets whether the given things were active in the last X days
     * @param thingIds of things to check activity of
     * @param days to classify recent activity by
     * @return active status of the given things in order
     */
    public List<Boolean> getWhetherThingsActive(List<String> thingIds, int days) {
        LocalDateTime lowerBound = LocalDateTime.now().minusDays(days);
        return thingIds.stream()
                .map(id -> datastreamRepository.findDatastreamsByThingId(id).stream()
                        .anyMatch(datastream -> datastream.getPhenomenonEnd().isAfter(lowerBound)))
                .collect(Collectors.toList());
    }

    /**
     * Gets active rate of things, calculated as amount of data transmissions / days
     * @param thingsIds of things to get active rate of
     * @param frameStart start of time frame to calculate active rate from
     * @param frameEnd end of time frame to calculate active rate from
     * @return active rate of the given things in order
     */
    public List<Double> getActiveRateOfThings(List<String> thingsIds, LocalDateTime frameStart, LocalDateTime frameEnd) {
        double days = Duration.between(frameStart, frameEnd).toDays();
        return thingsIds.stream()
                .map(id -> observationService.getObservationsByThingId(id, Integer.MAX_VALUE, Sort.unsorted(),
                        (List<String>) null,
                        frameStart,
                        frameEnd).size() / days)
                .collect(Collectors.toList());
    }

    public List<ObservationStats> getObservationStatsOfThings(List<String> thingsIds, List<String> obsIds,
                                                              LocalDateTime frameStart, LocalDateTime frameEnd) {
        return thingsIds.stream()
                .map(thongId->{
                    ObservationStats r = new ObservationStats();
                    for(String obsId : obsIds){
                        r.addObservedProperty(obsId, observationService.getObservationsByThingId(thongId,
                                Integer.MAX_VALUE, Sort.unsorted(), List.of(obsId), frameStart, frameEnd).stream()
                                .map(Observation::getResultNumber).collect(Collectors.toList()));
                    }
                    return r;
                })
                .collect(Collectors.toList());
    }

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
    public List<Thing> getListOfClosestSensors(double lat, double lon, double el) {
        HashMap<Double, Thing> map = new HashMap<>();
        List<Thing> result = new ArrayList<>();
        for (Thing thing : thingRepository.getAllBy()) {
            for (Location s : thing.getLocations()) {
                JSONObject jsonObject = new JSONObject(s.getLocation());
                JSONArray jsonArray = (JSONArray) jsonObject.get("coordinates");
                for (int i = 0; i <= 2; i++) {
                    try {
                        jsonArray.getDouble(i);
                    } catch (Exception ex) {
                        jsonArray.put(0D);
                    }
                }
                map.put(calculateDistanceFromCoordinates(lat, lon, jsonArray.getDouble(0), jsonArray.getDouble(1), el, jsonArray.getDouble(2)), thing);
            }
        }

        SortedSet<Double> keys = new TreeSet<>(map.keySet());
        for (Double key : keys) {
            Thing value = map.get(key);
            result.add(value);
        }
        return result;
    }


    /**
     * This calculates the distance between two coordinates on earth. Each coordinate consists of
     * three different values.
     *
     * @param lat1 latitude of the first point
     * @param lon1 longitude of the first point
     * @param lat2 latitude of the second point
     * @param lon2 longitude of the second point
     * @param el1  elevation of the first point
     * @param el2  elevation of the second point
     * @return The distance in meters as a double
     */
    private double calculateDistanceFromCoordinates(double lat1, double lon1, double lat2,
                                                    double lon2, double el1, double el2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Gets all tings, skr
     * @return all things in the entire universe
     */
    public List<Thing> getAllThings() {
        return thingRepository.findAll();
    }
}
