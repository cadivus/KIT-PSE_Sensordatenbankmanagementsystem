package edu.teco.sensordatenbankmanagementsystem.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.teco.sensordatenbankmanagementsystem.models.Location;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ThingService {

  private final ThingRepository thingRepository;
  private final DatastreamRepository datastreamRepository;

  @Autowired
  public ThingService(
      ThingRepository thingRepository,
      DatastreamRepository datastreamRepository) {
    this.thingRepository = thingRepository;
    this.datastreamRepository = datastreamRepository;
  }

  @Transactional
  public Thing getThing(String id) {
    return thingRepository.findById(id).get();
  }

  public List<Boolean> getWhetherThingsActive(List<String> ids, int days) {
    LocalDateTime lowerBound = LocalDateTime.now().minusDays(days);
    return ids.stream()
            .map(id->datastreamRepository.findDatastreamsByThingId(id).stream()
                    .anyMatch(datastream -> datastream.getPhenomenonEnd().isAfter(lowerBound)))
            .collect(Collectors.toList());
  }

  /**
   * This gets the list of all sensors (or things) from the database and orders them by distance
   * to a specified point
   * @param lat Latitude of the point
   * @param lon Longitude of the point
   * @param el elevation of the point
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
        map.put(calculateDistanceFromCoordinates(lat,lon,jsonArray.getDouble(0), jsonArray.getDouble(1), el, jsonArray.getDouble(2)), thing);
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
   * @param lat1 latitude of the first point
   * @param lon1 longitude of the first point
   * @param lat2 latitude of the second point
   * @param lon2 longitude of the second point
   * @param el1 elevation of the first point
   * @param el2 elevation of the second point
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
}
