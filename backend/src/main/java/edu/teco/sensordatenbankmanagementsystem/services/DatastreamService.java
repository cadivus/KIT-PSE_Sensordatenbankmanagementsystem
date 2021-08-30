package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface DatastreamService {

  /**
   * This gets a list of datastreams by their respective Thing
   * @param id The String representation of a Things unique identifier
   * @return A List of Datastreams
   */
  public List<Datastream> getDatastreamsByThing(String id);

  /**
   * This gets a Thing from the database by its datastreams ID and returns the first Datastream
   * match
   * @param id The Datastreams unique identifier
   * @return A Datastream
   */
  public Datastream getDatastream(String id);

  public Stream<Datastream> getDatastreams(List<String> thingIds, LocalDateTime start, LocalDateTime end);

}
