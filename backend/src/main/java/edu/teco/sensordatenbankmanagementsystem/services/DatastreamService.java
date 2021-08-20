package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import java.util.List;

public interface DatastreamService {

  public List<Datastream> getDatastreamsByThing(String id);

  public Datastream getDatastream(String id);


}
