package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import java.util.List;

public interface ThingService {

  /**
   *
   */
  public Thing getThing(String id);

  /**
   * This returns a list of the closest physical sensors to a given location.
   * @param lat
   * @param lon
   * @param el
   * @return
   */
  public List<Thing> getListOfClosestSensors(double lat, double lon, double el);

}
