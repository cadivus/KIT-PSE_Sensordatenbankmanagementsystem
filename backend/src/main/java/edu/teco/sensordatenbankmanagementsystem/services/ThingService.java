package edu.teco.sensordatenbankmanagementsystem.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.teco.sensordatenbankmanagementsystem.models.Location;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//TODO This might possibly not be needed
@Service
public class ThingService {

  private final ThingRepository thingRepository;

  @Autowired
  public ThingService(
      ThingRepository thingRepository) {
    this.thingRepository = thingRepository;
  }

  @Transactional
  public Thing getThing(String id) {
    return thingRepository.findById(id).get();
  }

  public List<Thing> getListOfClosestSensors(double lat, double lon) {
    HashMap<Double, Thing> map = new HashMap<>();
    List<Thing> result = new ArrayList<>();
    for (Thing thing : thingRepository.getAllBy()) {
      for (Location s : thing.getLocations()) {
        JSONObject jsonObject = new JSONObject(s.getLocation());
        JSONArray jsonArray = (JSONArray) jsonObject.get("coordinates");
        map.put(calculateDistanceFromCoordinates(lat,lon,jsonArray.getDouble(0), jsonArray.getDouble(1)), thing);
      }
    }

    SortedSet<Double> keys = new TreeSet<>(map.keySet());
    for (Double key : keys) {
      Thing value = map.get(key);
      result.add(value);
    }
    return result;
  }

  private double calculateDistanceFromCoordinates(double lat1, double lon1, double lat2,
      double lon2) {
    final int R = 6371; // Radius of the earth

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c * 1000; // convert to meters

    distance = Math.pow(distance, 2);

    return Math.sqrt(distance);
  }
}
